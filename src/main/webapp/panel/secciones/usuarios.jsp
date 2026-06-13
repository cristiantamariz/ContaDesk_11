<%@ page contentType="text/html; charset=UTF-8" isELIgnored="true" %>

<div id="section-usuarios" style="display:none">
    <div id="usuarios-toast" class="toast" style="display:none"></div>

    <div class="panel">
        <div class="panel-header">
            <span class="panel-title">Usuarios del sistema</span>
            <div class="panel-header-actions">
                <div class="search-wrapper">
                    <input type="text" id="usuarios-search" class="input-search" placeholder="Buscar…" oninput="filtrarUsuarios(this.value)">
                </div>
            </div>
        </div>
        <div class="panel-body">
            <table id="usuarios-table">
                <thead>
                    <tr>
                        <th>Usuario</th>
                        <th>Nombre completo</th>
                        <th>Email</th>
                        <th>Rol</th>
                        <th>Estado</th>
                        <th>Último acceso</th>
                        <th style="text-align:right">Acciones</th>
                    </tr>
                </thead>
                <tbody id="usuarios-tbody">
                    <tr><td colspan="7" class="empty">Cargando usuarios...</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <div id="modal-usuario" class="modal-overlay" style="display:none">
        <div class="modal-box modal-lg">
            <div class="modal-header">
                <div class="modal-header-left">
                    <span id="modal-usuario-title">Nuevo Usuario</span>
                </div>
                <button class="modal-close" onclick="cerrarModalUsuario()">✖</button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="usu-id">

                <div class="form-grid-2">
                    <div class="form-group">
                        <label>Username <span class="required">*</span></label>
                        <input type="text" id="usu-username" class="form-input">
                    </div>
                    <div class="form-group">
                        <label>Nombre Completo</label>
                        <input type="text" id="usu-nombre_completo" class="form-input">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" id="usu-email" class="form-input">
                    </div>
                    <div class="form-group">
                        <label>Rol</label>
                        <select id="usu-rol" class="form-input">
                            <option value="1">Administrador</option>
                            <option value="2">Contador</option>
                            <option value="3">Capturista</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Estado</label>
                        <select id="usu-activo" class="form-input">
                            <option value="true">Activo</option>
                            <option value="false">Inactivo</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn" onclick="cerrarModalUsuario()">Cancelar</button>
                <button class="btn btn-primary" onclick="guardarUsuario()">Guardar</button>
            </div>
        </div>
    </div>
</div>