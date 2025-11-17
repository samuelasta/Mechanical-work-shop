package co.uniquindio.edu.util;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.exception.BadRequestException;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;

public class PdfGeneratorUtil {

    public static byte[] generarPDFOrdenes(List<ObtenerOrdenDTO> ordenes) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // TITULO

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(33, 66, 99));
            Paragraph title = new Paragraph("Listado General de Órdenes", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // TABLA

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.5f, 2, 2.2f, 4, 4, 1.3f});

            // ENCABEZADOS

            Stream.of("ID", "Fecha Ingreso", "Estado", "Diagnostico inicial", "Diagnóstico Final", "Vehiculo")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.HELVETICA, 12, Font.BOLD)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor(new Color(230, 230, 250));
                        cell.setPadding(6);
                        table.addCell(cell);
                    });


            // LOS DATOS QUE VAN DEBAJO DE CADA ENCABEZADO

            for (ObtenerOrdenDTO orden : ordenes) {
                table.addCell(new Phrase(orden.id()));
                table.addCell(new Phrase(orden.fechaIngreso().toString()));
                table.addCell(new Phrase(orden.estado().name()));
                table.addCell(new Phrase(orden.diagnosticoInicial() != null ? orden.diagnosticoInicial() : "No registrado"));
                table.addCell(new Phrase(orden.diagnosticoFinal() != null ? orden.diagnosticoFinal() : "No registrado"));
                table.addCell(new Phrase(orden.placa()));
            }

            document.add(table);
            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new BadRequestException("Error generando PDF de órdenes");
        }
    }

    // 2
    public static byte[] generarPDFMecanicos(List<ObtenerMecanicoOrdenDTO> mecanicos) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // TITULO

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(33, 66, 99));
            Paragraph title = new Paragraph("Listado de mecanicos en la orden", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // TABLA

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2, 2, 2, 2, 3, 2.5f, 1.5f});

            // ENCABEZADOS

            Stream.of("ID", "Nombre1", "Nombre2", "Apellido1", "Apellido2", "Email", "Rol", "Experiencia (años)")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.HELVETICA, 12, Font.BOLD)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor(new Color(230, 230, 250));
                        cell.setPadding(6);
                        table.addCell(cell);
                    });


            // LOS DATOS QUE VAN DEBAJO DE CADA ENCABEZADO

            for (ObtenerMecanicoOrdenDTO mecanico : mecanicos) {
                table.addCell(new Phrase(mecanico.id()));
                table.addCell(new Phrase(mecanico.nombre1()));
                table.addCell(new Phrase(mecanico.nombre2() != null ? mecanico.nombre2() : "No registrado"));
                table.addCell(new Phrase(mecanico.apellido1()));
                table.addCell(new Phrase(mecanico.apellido2() != null ? mecanico.apellido2() : "No registrado"));
                table.addCell(new Phrase(mecanico.email()));
                table.addCell(new Phrase(mecanico.rolDTO()));
                table.addCell(new Phrase(mecanico.experiencia() + ""));
            }

            document.add(table);
            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new BadRequestException("Error generando PDF de órdenes");
        }

    }

    public static byte[] generarPDFListaServicios(List<ObtenerServicioDTO> servicios) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // TITULO

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(33, 66, 99));
            Paragraph title = new Paragraph("Listado de servicios disponibles", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // TABLA

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.5f, 3, 4, 3});

            // ENCABEZADOS

            Stream.of("ID", "Servicio", "Descripción", "Costo")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.HELVETICA, 12, Font.BOLD)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor(new Color(230, 230, 250));
                        cell.setPadding(6);
                        table.addCell(cell);
                    });


            // LOS DATOS QUE VAN DEBAJO DE CADA ENCABEZADO

            for (ObtenerServicioDTO servicio : servicios) {
                table.addCell(new Phrase(servicio.id()));
                table.addCell(new Phrase(servicio.tipoServicio().name()));
                table.addCell(new Phrase(servicio.descripcion()));
                table.addCell(new Phrase(servicio.costoUnitario() + ""));

            }

            document.add(table);
            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new BadRequestException("Error generando PDF de órdenes");
        }
    }

    public static byte[] generarPDFRepuestos(List<ObtenerRepuestoDTO> repuestos) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // TITULO

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(33, 66, 99));
            Paragraph title = new Paragraph("Listado de repuestos disponibles", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // TABLA

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.5f, 4, 3, 3});

            // ENCABEZADOS

            Stream.of("ID", "Repuesto", "Costo unitario", "Disponibles")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.HELVETICA, 12, Font.BOLD)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor(new Color(230, 230, 250));
                        cell.setPadding(6);
                        table.addCell(cell);
                    });


            // LOS DATOS QUE VAN DEBAJO DE CADA ENCABEZADO

            for (ObtenerRepuestoDTO repuesto : repuestos) {
                table.addCell(new Phrase(repuesto.id()));
                table.addCell(new Phrase(repuesto.nombre()));
                table.addCell(new Phrase(repuesto.costoUnitario() + ""));
                table.addCell(new Phrase(repuesto.stock() + ""));

            }

            document.add(table);
            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new BadRequestException("Error generando PDF de órdenes");
        }
    }

    public static byte[] generarPDFListaOrdenesCliente(List<ObtenerOrdenDTO> ordenes) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // TITULO

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(33, 66, 99));
            Paragraph title = new Paragraph("Listado de ordenes del cliente", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // TABLA

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.5f, 2, 2.2f, 4, 4, 1.3f});

            // ENCABEZADOS

            Stream.of("ID", "Fecha Ingreso", "Estado", "Diagnostico inicial", "Diagnóstico Final", "Vehiculo")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.HELVETICA, 12, Font.BOLD)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor(new Color(230, 230, 250));
                        cell.setPadding(6);
                        table.addCell(cell);
                    });


            // LOS DATOS QUE VAN DEBAJO DE CADA ENCABEZADO

            for (ObtenerOrdenDTO orden : ordenes) {
                table.addCell(new Phrase(orden.id()));
                table.addCell(new Phrase(orden.fechaIngreso().toString()));
                table.addCell(new Phrase(orden.estado().name()));
                table.addCell(new Phrase(orden.diagnosticoInicial() != null ? orden.diagnosticoInicial() : "No registrado"));
                table.addCell(new Phrase(orden.diagnosticoFinal() != null ? orden.diagnosticoFinal() : "No registrado"));
                table.addCell(new Phrase(orden.placa()));
            }

            document.add(table);
            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new BadRequestException("Error generando PDF de órdenes");
        }
    }
}

