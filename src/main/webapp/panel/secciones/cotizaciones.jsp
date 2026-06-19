<%@ page contentType="text/html; charset=UTF-8" isELIgnored="true" %>

<div id="section-cotizaciones" style="display:none">
    <div class="panel">
        <div class="panel-header">
            <span class="panel-title">Historial de Cotizaciones</span>
            <div class="panel-header-actions">
                <div class="search-wrapper">
                    <svg class="search-icon" width="14" height="14" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
                    <input type="text" id="cotizaciones-search" class="input-search" placeholder="Buscar cotización...">
                </div>
                <button class="btn btn-primary" onclick="abrirModalCotizador()">+ Nueva Cotización</button>
            </div>
        </div>
        <div class="panel-body">
            <table id="cotizaciones-table">
                <thead>
                    <tr>
                        <th>Cliente / Prospecto</th>
                        <th>Régimen Fiscal</th>
                        <th>Contador Asignado</th>
                        <th>Monto Mensual</th>
                        <th>Estatus</th>
                        <th style="text-align:right">Acciones</th>
                    </tr>
                </thead>
                <tbody id="cotizaciones-tbody">
                    <tr><td colspan="6" class="empty">Cargando cotizaciones...</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <%-- ── Modal del Motor de Cotización ── --%>
    <div id="modal-cotizador" class="modal-overlay" style="display:none">
        <div class="modal-box modal-lg" id="modal-cotizador-box">
            <div class="modal-header">
                <div class="modal-header-left">
                    <div class="modal-icon">
                        <svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><polyline points="10 9 9 9 8 9"/></svg>
                    </div>
                    <span id="modal-cotizador-title">Generar Cotización</span>
                </div>
                <button class="modal-close" onclick="cerrarModalCotizador()">&#x2716;</button>
            </div>

            <div class="modal-body">
                <%-- Campo oculto para el ID (usado al editar) --%>
                <input type="hidden" id="cot-id">

                <div class="form-section-label">1. Datos del Cliente</div>
                <div class="form-group" style="margin-bottom: 16px;">
                    <label>Cliente o Prospecto <span class="required">*</span></label>
                    <div class="select-wrapper">
                        <select id="cot-cliente" class="form-input" onchange="calcularCotizacion()">
                            <option value="">Seleccione un cliente / prospecto...</option>
                        </select>
                        <svg class="select-arrow" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M6 9l6 6 6-6"/></svg>
                    </div>
                </div>

                <div class="form-section-label" style="margin-top: 10px;">2. Asignación y Carga de Trabajo</div>
                <div class="form-group" style="margin-bottom: 14px;">
                    <label>Contador Asignado (Responsable) <span class="required">*</span></label>
                    <div class="select-wrapper">
                        <select id="cot-empleado" class="form-input" onchange="calcularCotizacion()">
                            <option value="">Asignar contador...</option>
                        </select>
                        <svg class="select-arrow" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M6 9l6 6 6-6"/></svg>
                    </div>
                </div>

                <div class="form-grid-2">
                    <div class="form-group">
                        <label>Promedio Facturas / Mes</label>
                        <input type="number" id="cot-volumen" class="form-input" min="0" value="0" oninput="calcularCotizacion()" placeholder="Ej. 45">
                    </div>
                    <div class="form-group">
                        <label>Horas Estimadas / Mes</label>
                        <input type="number" id="cot-horas" class="form-input" min="0" value="0" oninput="calcularCotizacion()" placeholder="Ej. 10">
                    </div>
                </div>

                <div id="cot-error" class="form-error" style="display:none; margin-top:15px;"></div>

                <%-- Área de visualización del cálculo --%>
                <div style="margin-top: 24px; padding: 18px; background: #eff6ff; border: 1px solid #bfdbfe; border-radius: 8px; text-align: center;">
                    <div style="font-size: 11px; font-weight: 600; color: #3b82f6; text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 4px;">
                        Tarifa Sugerida (Mensual)
                    </div>
                    <div id="cot-total" style="font-size: 32px; font-weight: 700; color: #1e3a8a; letter-spacing: -0.5px;">
                        $0.00
                    </div>
                    <div style="font-size: 12px; color: #60a5fa; margin-top: 6px;">
                        Cálculo basado en régimen, volumen y costo por hora.
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button class="btn" onclick="cerrarModalCotizador()">Cancelar</button>
                <button class="btn btn-primary" onclick="guardarCotizacion()">Guardar Cotización</button>
            </div>
        </div>
    </div>
</div>
