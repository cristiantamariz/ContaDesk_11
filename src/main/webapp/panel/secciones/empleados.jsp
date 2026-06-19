<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="section-empleados" style="display:none">
    <div class="panel">
        <div class="panel-header">
            <span class="panel-title">Directorio de Empleados</span>
            <div class="panel-header-actions">
                <div class="search-wrapper">
                    <svg class="search-icon" width="14" height="14" fill="none" stroke="currentColor" stroke-width="1.8" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
                    <input type="text" id="empleados-search" class="input-search" placeholder="Buscar empleado..." oninput="filtrarEmpleados(this.value)">
                </div>
            </div>
        </div>
        <div class="panel-body">
            <table id="empleados-table">
                <thead>
                    <tr>
                        <th>No. Emp</th>
                        <th>Nombre</th>
                        <th>Departamento</th>
                        <th>Puesto</th>
                        <th>Salario</th>
                        <th>Estado</th>
                        <th style="text-align:right">Acciones</th>
                    </tr>
                </thead>
                <tbody id="empleados-tbody">
                    <%-- Los datos se inyectarán dinámicamente vía AJAX desde app.js --%>
                </tbody>
            </table>
        </div>
    </div>

    <%-- Modal de Empleado --%>
    <div id="modal-empleado" class="modal-overlay" style="display:none">
        <div class="modal-box modal-lg" id="modal-empleado-box">
            <div class="modal-header">
                <div class="modal-header-left">
                    <span id="modal-empleado-title">Nuevo Empleado</span>
                </div>
                <button class="modal-close" onclick="cerrarModalEmpleado()">✖</button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="emp-id">

                <div class="form-grid-2">
                    <div class="form-group">
                        <label>No. Empleado <span class="required">*</span></label>
                        <input type="text" id="emp-numero" class="form-input">
                    </div>
                    <div class="form-grid-2" style="grid-template-columns: 1fr; gap: 0;">
                        <div class="form-group">
                            <label>Fecha Contratación <span class="required">*</span></label>
                            <input type="date" id="emp-fecha" class="form-input">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Nombre <span class="required">*</span></label>
                        <input type="text" id="emp-nombre" class="form-input">
                    </div>
                    <div class="form-group">
                        <label>Apellido Paterno</label>
                        <input type="text" id="emp-paterno" class="form-input">
                    </div>
                    <div class="form-group">
                        <label>Departamento</label>
                        <input type="text" id="emp-depto" class="form-input">
                    </div>
                    <div class="form-group">
                        <label>Puesto</label>
                        <input type="text" id="emp-puesto" class="form-input">
                    </div>
                    <div class="form-group">
                        <label>Salario Base (Mensual)</label>
                        <input type="number" id="emp-salario" class="form-input" step="0.01">
                    </div>
                    <div class="form-group">
                        <label>Estado</label>
                        <div class="select-wrapper">
                            <select id="emp-activo" class="form-input">
                                <option value="true">Activo</option>
                                <option value="false">Baja</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div id="emp-error" class="form-error" style="display:none; margin-top:15px;"></div>
            </div>
            <div class="modal-footer">
                <button class="btn" onclick="cerrarModalEmpleado()">Cancelar</button>
                <button class="btn btn-primary" onclick="guardarEmpleado()">Guardar Empleado</button>
            </div>
        </div>
    </div>
</div>