  <%@ page contentType="text/html; charset=UTF-8" %>
  <div id="section-clientes" style="display:none">
      <div class="panel">
          <div class="panel-header">
              <span class="panel-title">Clientes</span>
              <input type="text" class="input-search" placeholder="Buscar clientes..." oninput="filtrarClientes(this.value)">
          </div>
          <div class="panel-body">
              <table id="clientes-table">
                  <thead>
                      <tr>
                          <th>Nombre / Razón Social</th>
                          <th>RFC</th>
                          <th>Email</th>
                          <th>Teléfono</th>
                          <th>Estado</th>
                          <th style="text-align:right">Acciones</th>
                      </tr>
                  </thead>
                  <tbody id="clientes-tbody">
                      <tr><td colspan="6" class="empty">Cargando clientes...</td></tr>
                  </tbody>
              </table>
          </div>
      </div>

      <%-- Modal Clientes --%>
      <div id="modal-cliente" class="modal-overlay" style="display:none">
          <div class="modal-box modal-lg">
              <div class="modal-header">
                  <span id="modal-cliente-title">Nuevo Cliente</span>
                  <button class="modal-close" onclick="cerrarModalCliente()">✖</button>
              </div>
              <div class="modal-body">
                  <input type="hidden" id="cli-id">
                  <div class="form-grid-2">
                      <div class="form-group"><label>Nombre <span class="required">*</span></label><input type="text" id="cli-nombre" class="form-input"></div>
                      <div class="form-group"><label>RFC <span class="required">*</span></label><input type="text" id="cli-rfc" class="form-input"></div>
                      <div class="form-group"><label>Razón Social</label><input type="text" id="cli-razon" class="form-input"></div>
                      <div class="form-group"><label>Email</label><input type="email" id="cli-email" class="form-input"></div>
                      <div class="form-group"><label>Teléfono</label><input type="text" id="cli-telefono" class="form-input"></div>
                      <div class="form-group"><label>C.P.</label><input type="text" id="cli-cp" class="form-input"></div>
                      <div class="form-group"><label>Dirección</label><input type="text" id="cli-direccion" class="form-input"></div>
                      <div class="form-group"><label>Régimen Fiscal</label><input type="text" id="cli-regimen" class="form-input"></div>
                  </div>
              </div>
              <div class="modal-footer">
                  <button class="btn" onclick="cerrarModalCliente()">Cancelar</button>
                  <button class="btn btn-primary" onclick="guardarCliente()">Guardar</button>
              </div>
          </div>
      </div>
  </div>