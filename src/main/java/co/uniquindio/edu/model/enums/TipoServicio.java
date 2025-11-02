package co.uniquindio.edu.model.enums;

public enum TipoServicio {
    // 🔹 Mantenimiento general
    MANTENIMIENTO_PREVENTIVO,      // cambio de aceite, filtros, revisión de niveles
    MANTENIMIENTO_CORRECTIVO,      // reparación de fallas mecánicas o eléctricas
    REVISION_TECNOMECANICA,        // revisión previa a la inspección obligatoria

    // 🔹 Mecánica
    MECANICA_GENERAL,              // motor, transmisión, suspensión
    SISTEMA_DE_FRENOS,             // cambio de pastillas, discos, purgado
    SUSPENSION_Y_DIRECCION,        // amortiguadores, rótulas, alineación
    CAMBIO_DE_EMBRAGUE,            // clutch, volante, sistema hidráulico

    // 🔹 Electricidad y electrónica
    ELECTRICIDAD_AUTOMOTRIZ,       // alternador, batería, cableado, luces
    DIAGNOSTICO_ELECTRONICO,       // escaneo con equipo de diagnóstico
    REPARACION_AIRE_ACONDICIONADO, // carga de gas, compresor, condensador

    // 🔹 Estética y carrocería
    LATONERIA,                     // enderezado de golpes, cambio de piezas
    PINTURA,                       // pintura parcial o completa
    LAVADO_DETALLADO,              // limpieza profunda interior y exterior
    PULIDO_Y_ENCERADO,             // abrillantado de pintura

    // 🔹 Servicios adicionales
    ALINEACION_Y_BALANCEO,         // corrección de dirección y balanceo de ruedas
    INSTALACION_ACCESORIOS,        // alarmas, luces, sensores, vidrios eléctricos
    MANTENIMIENTO_AIRE_ACONDICIONADO, // limpieza de filtros, revisión del sistema
    REVISION_GENERAL,              // inspección completa del vehículo
    MONTAJE_DE_LLANTAS,            // cambio, reparación y calibración
    CAMBIO_DE_ACEITE               // servicio rápido, incluye revisión de filtros
}
