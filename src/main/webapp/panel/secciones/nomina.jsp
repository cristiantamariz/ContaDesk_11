<%@ page contentType="text/html; charset=UTF-8" isELIgnored="true" %>

<div id="section-nomina" style="display:none">
    <div class="panel">
        <div class="panel-header">
            <span class="panel-title">Nómina Mensual</span>
            <div class="panel-header-actions">
                <div class="search-wrapper">
                    <svg class="search-icon" width="14" height="14" fill="none" stroke="currentColor"
                         stroke-width="1.8" viewBox="0 0 24 24">
                        <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
                    </svg>
                    <input type="text" id="nomina-search" class="input-search" placeholder="Buscar empleado...">
                </div>
                <button class="btn btn-primary" onclick="abrirModalNomina()">+ Generar Nómina</button>
            </div>
        </div>
        <div class="panel-body">
            <table id="nomina-table">
                <thead>
                    <tr>
                        <th>Empleado</th>
                        <th>Periodo</th>
                        <th>Sueldo Base</th>
                        <th>Bonos (3%)</th>
                        <th>Deducciones</th>
                        <th>Total Neto</th>
                        <th>Estado</th>
                        <th style="text-align:right">Acciones</th>
                    </tr>
                </thead>
                <tbody id="nomina-tbody">
                    <tr><td colspan="8" class="empty">Cargando nóminas...</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <%-- ── Modal Generador de Nómina ── --%>
    <div id="modal-nomina" class="modal-overlay" style="display:none">
        <div class="modal-box modal-lg">
            <div class="modal-header">
                <div class="modal-header-left">
                    <div class="modal-icon">
                        <svg width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"
                             viewBox="0 0 24 24">
                            <rect x="2" y="3" width="20" height="14" rx="2"/>
                            <line x1="8" y1="21" x2="16" y2="21"/>
                            <line x1="12" y1="17" x2="12" y2="21"/>
                        </svg>
                    </div>
                    <span id="modal-nomina-title">Generar Nómina</span>
                </div>
                <button class="modal-close" onclick="cerrarModalNomina()">&#x2716;</button>
            </div>

            <div class="modal-body">

                <div class="form-section-label">1. Selección de Empleado y Periodo</div>
                <div class="form-grid-2" style="margin-bottom:14px">
                    <div class="form-group">
                        <label>Empleado <span class="required">*</span></label>
                        <div class="select-wrapper">
                            <select id="nom-empleado" class="form-input">
                                <option value="">Seleccione un empleado...</option>
                            </select>
                            <svg class="select-arrow" width="14" height="14" fill="none" stroke="currentColor"
                                 stroke-width="2" viewBox="0 0 24 24"><path d="M6 9l6 6 6-6"/></svg>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Mes y Año <span class="required">*</span></label>
                        <input type="month" id="nom-periodo" class="form-input">
                    </div>
                </div>

                <div class="form-section-label">2. Deducciones</div>
                <div class="form-group" style="margin-bottom:16px">
                    <label>Deducciones (ISR + IMSS + otros)</label>
                    <input type="number" id="nom-deducciones" class="form-input"
                           min="0" value="0" placeholder="Ej. 3750.00">
                </div>

                <div style="text-align:right; margin-bottom:16px">
                    <button class="btn btn-primary" onclick="calcularNomina()">
                        &#9656; Calcular
                    </button>
                </div>

                <%-- Resultado del cálculo --%>
                <div id="nom-resultado" style="display:none; margin-top:8px;
                     padding:20px; background:#f0fdf4; border:1px solid #bbf7d0;
                     border-radius:8px;">

                    <div style="display:grid; grid-template-columns:1fr 1fr 1fr; gap:16px;
                                text-align:center; margin-bottom:16px;">
                        <div>
                            <div style="font-size:11px;font-weight:600;color:#6b7280;
                                        text-transform:uppercase;letter-spacing:.05em">
                                Sueldo Base
                            </div>
                            <div id="nom-res-sueldo"
                                 style="font-size:22px;font-weight:700;color:#111827">$0.00</div>
                        </div>
                        <div>
                            <div style="font-size:11px;font-weight:600;color:#6b7280;
                                        text-transform:uppercase;letter-spacing:.05em">
                                Bonos (3% cots. aprobadas)
                            </div>
                            <div id="nom-res-bonos"
                                 style="font-size:22px;font-weight:700;color:#16a34a">$0.00</div>
                        </div>
                        <div>
                            <div style="font-size:11px;font-weight:600;color:#6b7280;
                                        text-transform:uppercase;letter-spacing:.05em">
                                Deducciones
                            </div>
                            <div id="nom-res-deduc"
                                 style="font-size:22px;font-weight:700;color:#dc2626">$0.00</div>
                        </div>
                    </div>

                    <div style="border-top:1px solid #bbf7d0; padding-top:14px; text-align:center">
                        <div style="font-size:11px;font-weight:600;color:#16a34a;
                                    text-transform:uppercase;letter-spacing:.05em">
                            Total Neto a Pagar
                        </div>
                        <div id="nom-res-total"
                             style="font-size:34px;font-weight:800;color:#14532d;
                                    letter-spacing:-0.5px">
                            $0.00
                        </div>
                        <div id="nom-res-periodo"
                             style="font-size:12px;color:#6b7280;margin-top:4px"></div>
                    </div>
                </div>

                <%-- Campos ocultos con los datos del cálculo para enviar al guardar --%>
                <input type="hidden" id="nom-h-idEmpleado">
                <input type="hidden" id="nom-h-nombreEmpleado">
                <input type="hidden" id="nom-h-numeroEmpleado">
                <input type="hidden" id="nom-h-fechaInicio">
                <input type="hidden" id="nom-h-fechaFin">
                <input type="hidden" id="nom-h-ejercicio">
                <input type="hidden" id="nom-h-sueldoBase">
                <input type="hidden" id="nom-h-bonos">
                <input type="hidden" id="nom-h-totalNeto">

                <div id="nom-error" class="form-error" style="display:none;margin-top:12px"></div>
            </div>

            <div class="modal-footer">
                <button class="btn" onclick="cerrarModalNomina()">Cancelar</button>
                <button id="btn-guardar-nomina" class="btn btn-primary"
                        style="display:none" onclick="guardarNomina()">
                    Guardar Nómina
                </button>
            </div>
        </div>
    </div>
</div>
