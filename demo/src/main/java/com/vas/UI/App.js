async function fetchListado(path) {
  const res = await fetch(path, { headers: { Accept: "application/json" } });
  if (!res.ok) throw new Error(`Error ${res.status} en ${path}`);
  return res.json();
}

async function postForm(action, campos) {
  const body = new URLSearchParams(campos);
  const res = await fetch(action, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      Accept: "application/json",
    },
    body: body.toString(),
  });
  if (!res.ok) throw new Error(`Error ${res.status} en ${action}`);
  return res.json().catch(() => ({}));
}

function estadoBadge(estado) {
  const clases = {
    activo: "badge-green",
    Activo: "badge-green",
    "En progreso": "badge-blue",
    Finalizado: "badge-teal",
    Pendiente: "badge-amber",
  };
  return `<span class="badge ${clases[estado] || "badge-gray"}">${estado || ""}</span>`;
}

function plataformaBadge(p) {
  const c = { Instagram: "badge-teal", Facebook: "badge-blue" };
  return `<span class="badge ${c[p] || "badge-gray"}">${p || ""}</span>`;
}

function iniciales(nombre) {
  return (nombre || "?")
    .split(" ")
    .slice(0, 2)
    .map((w) => w[0])
    .join("")
    .toUpperCase();
}

function cargando(tbodyId, cols) {
  document.getElementById(tbodyId).innerHTML =
    `<tr><td colspan="${cols}" style="text-align:center;color:#888;padding:1.5rem">Cargando...</td></tr>`;
}

function errorFila(tbodyId, cols, msg) {
  document.getElementById(tbodyId).innerHTML =
    `<tr><td colspan="${cols}" style="text-align:center;color:#a32d2d;padding:1.5rem">⚠ ${msg}</td></tr>`;
}

function flashMsg(elId, tipo, texto) {
  const el = document.getElementById(elId);
  if (!el) return;
  el.textContent = texto;
  el.className = `flash-msg flash-${tipo}`;
  el.style.display = "block";
  setTimeout(() => {
    el.style.display = "none";
  }, 4000);
}

let _proyectosCache = [];

async function renderDashboard() {
  try {
    const [clientes, proyectos, empleados, campanas] = await Promise.all([
      fetchListado("/clientes/listado"),
      fetchListado("/proyectos/listado"),
      fetchListado("/empleados/listado"),
      fetchListado("/campanas/listado"),
    ]);
    document.getElementById("m-clientes").textContent = clientes.length;
    document.getElementById("m-proyectos").textContent = proyectos.length;
    document.getElementById("m-empleados").textContent = empleados.length;
    document.getElementById("m-campanas").textContent = campanas.length;
    document.getElementById("dash-tbody").innerHTML = proyectos
      .map(
        (p) => `
      <tr>
        <td>${p.nombreProyecto || p.nombre_proyecto || ""}</td>
        <td>${p.cliente || ""}</td>
        <td>${p.servicio || ""}</td>
        <td>${estadoBadge(p.estado)}</td>
        <td>$${p.precio || 0}</td>
      </tr>`,
      )
      .join("");
  } catch (e) {
    errorFila(
      "dash-tbody",
      5,
      "No se pudo conectar con el servidor. " + e.message,
    );
  }
}

async function renderClientes() {
  cargando("tbody-clientes", 7);
  try {
    const datos = await fetchListado("/clientes/listado");
    document.getElementById("tbody-clientes").innerHTML =
      datos
        .map((c) => {
          const nombre = c.nombreEmpresa || c.nombre_empresa || "";
          const id = c.idCliente || "";
          return `<tr>
        <td><span class="avatar">${iniciales(nombre)}</span>${nombre}</td>
        <td>${c.contacto || ""}</td>
        <td>${c.telefono || ""}</td>
        <td style="color:#2563a8">${c.email || ""}</td>
        <td>${c.direccion || ""}</td>
        <td>${estadoBadge(c.estado)}</td>
        <td><div class="row-actions">
          <a href="/clientes/modificar/${id}" class="row-btn">Editar</a>
          <button class="row-btn danger" onclick="eliminarCliente('${nombre.replace(/'/g, "\\'")}')">Eliminar</button>
        </div></td>
      </tr>`;
        })
        .join("") ||
      `<tr><td colspan="7" style="text-align:center;color:#aaa;padding:1.5rem">Sin clientes</td></tr>`;
  } catch (e) {
    errorFila("tbody-clientes", 7, e.message);
  }
}

async function eliminarCliente(nombreEmpresa) {
  if (!confirm(`¿Eliminar el cliente "${nombreEmpresa}"?`)) return;
  try {
    await postForm("/clientes/eliminar", { nombreEmpresa });
    flashMsg("msg-clientes", "ok", "Cliente eliminado exitosamente");
    renderClientes();
  } catch (e) {
    flashMsg("msg-clientes", "error", "No se pudo eliminar: " + e.message);
  }
}

async function renderServicios() {
  cargando("tbody-servicios", 4);
  try {
    const datos = await fetchListado("/servicios/listado");
    document.getElementById("tbody-servicios").innerHTML =
      datos
        .map((s) => {
          const nombre = s.nombreServicio || s.nombre_servicio || "";
          const precio = s.precioBase || s.precio_base || 0;
          return `<tr>
        <td style="font-weight:600">${nombre}</td>
        <td style="color:#555">${s.descripcion || ""}</td>
        <td>$${precio}</td>
        <td><div class="row-actions">
          <a href="/servicios/modificar/${encodeURIComponent(nombre)}" class="row-btn">Editar</a>
          <button class="row-btn danger" onclick="eliminarServicio('${nombre.replace(/'/g, "\\'")}')">Eliminar</button>
        </div></td>
      </tr>`;
        })
        .join("") ||
      `<tr><td colspan="4" style="text-align:center;color:#aaa;padding:1.5rem">Sin servicios</td></tr>`;
  } catch (e) {
    errorFila("tbody-servicios", 4, e.message);
  }
}

async function eliminarServicio(nombreServicio) {
  if (!confirm(`¿Eliminar el servicio "${nombreServicio}"?`)) return;
  try {
    await postForm("/servicios/eliminar", { nombreServicio });
    flashMsg("msg-servicios", "ok", "Servicio eliminado exitosamente");
    renderServicios();
  } catch (e) {
    flashMsg("msg-servicios", "error", "No se pudo eliminar: " + e.message);
  }
}

async function renderEmpleados() {
  cargando("tbody-empleados", 5);
  try {
    const datos = await fetchListado("/empleados/listado");
    document.getElementById("tbody-empleados").innerHTML =
      datos
        .map((e) => {
          const id = e.idEmpleado || "";
          return `<tr>
        <td><span class="avatar avatar-blue">${iniciales(e.nombre)}</span>${e.nombre || ""}</td>
        <td>${e.puesto || ""}</td>
        <td>${e.especialidad || ""}</td>
        <td style="color:#2563a8">${e.email || ""}</td>
        <td><div class="row-actions">
          <a href="/empleados/modificar/${id}" class="row-btn">Editar</a>
          <button class="row-btn danger" onclick="eliminarEmpleado('${(e.email || "").replace(/'/g, "\\'")}')">Eliminar</button>
        </div></td>
      </tr>`;
        })
        .join("") ||
      `<tr><td colspan="5" style="text-align:center;color:#aaa;padding:1.5rem">Sin empleados</td></tr>`;
  } catch (e) {
    errorFila("tbody-empleados", 5, e.message);
  }
}

async function eliminarEmpleado(email) {
  if (!confirm(`¿Eliminar el empleado con email "${email}"?`)) return;
  try {
    await postForm("/empleados/eliminar", { email });
    renderEmpleados();
  } catch (e) {
    alert("No se pudo eliminar: " + e.message);
  }
}

async function renderProyectos() {
  cargando("tbody-proyectos", 6);
  try {
    const datos = await fetchListado("/proyectos/listado");
    _proyectosCache = datos;
    pintarProyectos(datos);
  } catch (e) {
    errorFila("tbody-proyectos", 6, e.message);
  }
}

function pintarProyectos(datos) {
  document.getElementById("tbody-proyectos").innerHTML =
    datos
      .map((p) => {
        const nombre = p.nombreProyecto || p.nombre_proyecto || "";
        return `<tr>
      <td style="font-weight:600">${nombre}</td>
      <td>${p.cliente || ""}</td>
      <td>${p.servicio || ""}</td>
      <td>${estadoBadge(p.estado)}</td>
      <td>$${p.precio || 0}</td>
      <td><div class="row-actions">
        <button class="row-btn danger" onclick="eliminarProyecto('${nombre.replace(/'/g, "\\'")}')">Eliminar</button>
      </div></td>
    </tr>`;
      })
      .join("") ||
    `<tr><td colspan="6" style="text-align:center;color:#aaa;padding:1.5rem">Sin proyectos</td></tr>`;
}

function filtrarProyectos() {
  const q = (
    document.getElementById("search-proyectos")?.value || ""
  ).toLowerCase();
  pintarProyectos(
    _proyectosCache.filter(
      (p) =>
        (p.nombreProyecto || p.nombre_proyecto || "")
          .toLowerCase()
          .includes(q) || (p.cliente || "").toLowerCase().includes(q),
    ),
  );
}

async function eliminarProyecto(nombreProyecto) {
  if (!confirm(`¿Eliminar el proyecto "${nombreProyecto}"?`)) return;
  try {
    await postForm("/proyectos/eliminar", { nombreProyecto });
    renderProyectos();
  } catch (e) {
    alert("No se pudo eliminar: " + e.message);
  }
}

async function renderCampanas() {
  cargando("tbody-campanas", 5);
  try {
    const datos = await fetchListado("/campanas/listado");
    document.getElementById("tbody-campanas").innerHTML =
      datos
        .map((c) => {
          const tipo = c.tipoCampana || c.tipo_campana || "";
          return `<tr>
        <td>${c.cliente || ""}</td>
        <td>${plataformaBadge(c.plataforma)}</td>
        <td>${tipo}</td>
        <td>$${c.presupuesto || 0}</td>
        <td><div class="row-actions">
          <button class="row-btn danger" onclick="eliminarCampana('${tipo.replace(/'/g, "\\'")}')">Eliminar</button>
        </div></td>
      </tr>`;
        })
        .join("") ||
      `<tr><td colspan="5" style="text-align:center;color:#aaa;padding:1.5rem">Sin campañas</td></tr>`;
  } catch (e) {
    errorFila("tbody-campanas", 5, e.message);
  }
}

async function eliminarCampana(tipoCampana) {
  if (!confirm(`¿Eliminar la campaña "${tipoCampana}"?`)) return;
  try {
    await postForm("/campanas/eliminar", { tipoCampana });
    renderCampanas();
  } catch (e) {
    alert("No se pudo eliminar: " + e.message);
  }
}

async function renderTareas() {
  cargando("tbody-tareas", 5);
  try {
    const datos = await fetchListado("/tareas/listado");
    document.getElementById("tbody-tareas").innerHTML =
      datos
        .map((t) => {
          const id = t.idTarea || "";
          return `<tr>
        <td style="font-weight:600">${t.titulo || ""}</td>
        <td>${t.proyecto || ""}</td>
        <td>${t.empleado || ""}</td>
        <td>${estadoBadge(t.estado)}</td>
        <td><div class="row-actions">
          <a href="/tareas/modificar/${id}" class="row-btn">Editar</a>
          <button class="row-btn danger" onclick="eliminarTarea('${(t.titulo || "").replace(/'/g, "\\'")}')">Eliminar</button>
        </div></td>
      </tr>`;
        })
        .join("") ||
      `<tr><td colspan="5" style="text-align:center;color:#aaa;padding:1.5rem">Sin tareas</td></tr>`;
  } catch (e) {
    errorFila("tbody-tareas", 5, e.message);
  }
}

async function eliminarTarea(titulo) {
  if (!confirm(`¿Eliminar la tarea "${titulo}"?`)) return;
  try {
    await postForm("/tareas/eliminar", { titulo });
    renderTareas();
  } catch (e) {
    alert("No se pudo eliminar: " + e.message);
  }
}

const renders = {
  dashboard: renderDashboard,
  clientes: renderClientes,
  servicios: renderServicios,
  empleados: renderEmpleados,
  proyectos: renderProyectos,
  campanas: renderCampanas,
  tareas: renderTareas,
};

const modalConfig = {
  clientes: {
    titulo: "Agregar cliente",
    action: "/clientes/guardar",
    campos: `
      <div class="form-grid">
        <div class="form-group full">
          <label>Nombre de la empresa</label>
          <input name="nombreEmpresa" placeholder="Ej: Restaurante La Terraza" required>
        </div>
        <div class="form-group">
          <label>Contacto</label>
          <input name="contacto" placeholder="Nombre del contacto" required>
        </div>
        <div class="form-group">
          <label>Teléfono</label>
          <input name="telefono" placeholder="88880000" required>
        </div>
        <div class="form-group">
          <label>Email</label>
          <input name="email" type="email" placeholder="correo@empresa.com" required>
        </div>
        <div class="form-group">
          <label>Dirección</label>
          <input name="direccion" placeholder="Provincia" required>
        </div>
        <div class="form-group">
          <label>Estado</label>
          <select name="estado">
            <option value="activo">activo</option>
            <option value="inactivo">inactivo</option>
          </select>
        </div>
      </div>`,
  },
  servicios: {
    titulo: "Agregar servicio",
    action: "/servicios/guardar",
    campos: `
      <div class="form-grid">
        <div class="form-group full">
          <label>Nombre del servicio</label>
          <input name="nombreServicio" placeholder="Ej: Desarrollo Web" required>
        </div>
        <div class="form-group full">
          <label>Descripción</label>
          <input name="descripcion" placeholder="Descripción breve" required>
        </div>
        <div class="form-group">
          <label>Precio base ($)</label>
          <input name="precioBase" type="number" min="0" placeholder="0" required>
        </div>
      </div>`,
  },
  empleados: {
    titulo: "Agregar empleado",
    action: "/empleados/guardar",
    campos: `
      <div class="form-grid">
        <div class="form-group">
          <label>Nombre completo</label>
          <input name="nombre" placeholder="Nombre del empleado" required>
        </div>
        <div class="form-group">
          <label>Puesto</label>
          <input name="puesto" placeholder="Ej: Desarrollador Web" required>
        </div>
        <div class="form-group">
          <label>Especialidad</label>
          <input name="especialidad" placeholder="Ej: Frontend" required>
        </div>
        <div class="form-group">
          <label>Email</label>
          <input name="email" type="email" placeholder="correo@vasolutions.com" required>
        </div>
      </div>`,
  },
  proyectos: {
    titulo: "Agregar proyecto",
    action: "/proyectos/guardar",
    campos: `
      <div class="form-grid">
        <div class="form-group full">
          <label>Nombre del proyecto</label>
          <input name="nombreProyecto" placeholder="Ej: Página Web Restaurante" required>
        </div>
        <div class="form-group">
          <label>Cliente</label>
          <input name="cliente" placeholder="Empresa cliente" required>
        </div>
        <div class="form-group">
          <label>Servicio</label>
          <input name="servicio" placeholder="Ej: Desarrollo Web" required>
        </div>
        <div class="form-group">
          <label>Estado</label>
          <select name="estado">
            <option value="Activo">Activo</option>
            <option value="En progreso">En progreso</option>
            <option value="Finalizado">Finalizado</option>
            <option value="Pendiente">Pendiente</option>
          </select>
        </div>
        <div class="form-group">
          <label>Precio ($)</label>
          <input name="precio" type="number" min="0" placeholder="0" required>
        </div>
      </div>`,
  },
  campanas: {
    titulo: "Agregar campaña",
    action: "/campanas/guardar",
    campos: `
      <div class="form-grid">
        <div class="form-group">
          <label>Cliente</label>
          <input name="cliente" placeholder="Empresa cliente" required>
        </div>
        <div class="form-group">
          <label>Plataforma</label>
          <select name="plataforma">
            <option value="Instagram">Instagram</option>
            <option value="Facebook">Facebook</option>
          </select>
        </div>
        <div class="form-group full">
          <label>Tipo de campaña</label>
          <input name="tipoCampana" placeholder="Ej: Promoción restaurante" required>
        </div>
        <div class="form-group">
          <label>Presupuesto ($)</label>
          <input name="presupuesto" type="number" min="0" placeholder="0" required>
        </div>
      </div>`,
  },
  tareas: {
    titulo: "Agregar tarea",
    action: "/tareas/guardar",
    campos: `
      <div class="form-grid">
        <div class="form-group full">
          <label>Título de la tarea</label>
          <input name="titulo" placeholder="Ej: Diseño del sitio web" required>
        </div>
        <div class="form-group">
          <label>Proyecto</label>
          <input name="proyecto" placeholder="Nombre del proyecto" required>
        </div>
        <div class="form-group">
          <label>Empleado</label>
          <input name="empleado" placeholder="Nombre del empleado" required>
        </div>
        <div class="form-group">
          <label>Estado</label>
          <select name="estado">
            <option value="Pendiente">Pendiente</option>
            <option value="En progreso">En progreso</option>
            <option value="Activo">Activo</option>
            <option value="Finalizado">Finalizado</option>
          </select>
        </div>
      </div>`,
  },
};

function openModal() {
  const cfg = modalConfig[currentPage];
  if (!cfg) return;
  document.getElementById("modal-title").textContent = cfg.titulo;
  document.getElementById("modal-body").innerHTML = cfg.campos;
  document.getElementById("modal-form").action = cfg.action;
  document.getElementById("modal").classList.add("open");
}

function closeModal() {
  document.getElementById("modal").classList.remove("open");
}

document.getElementById("modal").addEventListener("click", function (e) {
  if (e.target === this) closeModal();
});

document
  .getElementById("modal-form")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const form = e.target;
    const datos = new URLSearchParams(new FormData(form));
    const btn = form.querySelector('button[type="submit"]');
    btn.textContent = "Guardando...";
    btn.disabled = true;
    try {
      const res = await fetch(form.action, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Accept: "application/json",
        },
        body: datos.toString(),
      });
      if (!res.ok) throw new Error("Error " + res.status);
      closeModal();
      renders[currentPage]();
    } catch (e) {
      alert("No se pudo guardar: " + e.message);
    } finally {
      btn.textContent = "Guardar";
      btn.disabled = false;
    }
  });

let currentPage = "dashboard";

const pageTitles = {
  dashboard: "Dashboard",
  clientes: "Clientes",
  servicios: "Servicios",
  empleados: "Empleados",
  proyectos: "Proyectos",
  campanas: "Campañas de Redes",
  tareas: "Tareas",
};

function showPage(nombre, el) {
  document
    .querySelectorAll(".page")
    .forEach((p) => p.classList.remove("active"));
  document
    .querySelectorAll(".nav-item")
    .forEach((n) => n.classList.remove("active"));
  document.getElementById("page-" + nombre).classList.add("active");
  el.classList.add("active");
  document.getElementById("page-title").textContent =
    pageTitles[nombre] || nombre;
  document.getElementById("add-btn").style.display =
    nombre !== "dashboard" ? "inline-block" : "none";
  currentPage = nombre;
  renders[nombre]();
}

renderDashboard();