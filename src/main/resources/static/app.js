const modules = {
  users: { label: 'Users', icon: '◉', endpoint: '/users', fields: [['name','Name','text'],['email','Email','email'],['password','Password','password'],['role','Role','select',['ADMIN','FACULTY','STUDENT','SECURITY','LIBRARIAN','PLACEMENT']],['lastLogin','Last login','datetime-local']], columns: ['id','name','email','role','lastLogin'] },
  students: { label: 'Students', icon: '◫', endpoint: '/students', fields: [['rollNo','Roll number','text'],['courseId','Course ID','number'],['semester','Semester','number']], columns: ['id','rollNo','courseId','semester'] },
  faculties: { label: 'Faculty', icon: '♙', endpoint: '/faculties', fields: [['empId','Employee ID','text'],['name','Name','text'],['department','Department','text'],['subject','Subject','text']], columns: ['id','empId','name','department','subject'] },
  courses: { label: 'Courses', icon: '▤', endpoint: '/courses', fields: [['name','Course name','text'],['code','Course code','text'],['facultyId','Faculty ID','number'],['semester','Semester','number']], columns: ['id','name','code','facultyId','semester'] },
  notices: { label: 'Notices', icon: '▱', endpoint: '/notices', fields: [['title','Title','text'],['body','Message','textarea'],['issuedForRoles','Visible to roles','text'],['validFrom','Valid from','datetime-local'],['validTo','Valid to','datetime-local'],['attachment','PDF attachment','file']], columns: ['id','title','body','issuedForRoles','validFrom','validTo','attachmentName'] },
  events: { label: 'Events', icon: '◇', endpoint: '/events', fields: [['title','Event title','text'],['startTime','Start time','datetime-local'],['endTime','End time','datetime-local'],['venue','Venue','text'],['attachment','PDF attachment','file']], columns: ['id','title','startTime','endTime','venue','attachmentName'] },
  resources: { label: 'Resources', icon: '▣', endpoint: '/resources', fields: [['name','Resource name','text'],['type','Type','text'],['capacity','Capacity','number']], columns: ['id','name','type','capacity'] },
  bookings: { label: 'Book bookings', icon: '▧', endpoint: '/bookings', fields: [['bookId','Book ID','number'],['bookedBy','Booked by (student ID)','number'],['startTime','Start time','datetime-local'],['endTime','End time','datetime-local'],['status','Status','select',['PENDING','APPROVED','REJECTED','CANCELLED']]], columns: ['id','bookId','bookedBy','startTime','endTime','status'] },
  books: { label: 'Library books', icon: '▥', endpoint: '/books', fields: [['id','Book ID','readonly'],['cover','Front Page','image'],['title','Title','text'],['availableCopies','Available copies','number'],['driveLink','Google Drive link','url']], columns: ['id','coverImagePath','title','availableCopies','driveLink'] },
  issues: { label: 'Book issues', icon: '↗', endpoint: '/issues', fields: [['bookId','Book ID','number'],['issuedTo','Issued to (user ID)','number'],['issueDate','Issue date','date'],['dueDate','Due date','date']], columns: ['id','bookId','issuedTo','issueDate','dueDate'] },
  librarians: { label: 'Librarians', icon: '♜', endpoint: '/librarians', fields: [['empId','Employee ID','text'],['name','Name','text'],['email','Email','email'],['password','Password','password'],['department','Department','text']], columns: ['id','empId','name','email','department'] },
  logs: { label: 'Entry logs', icon: '◷', endpoint: '/logs', fields: [['userId','User ID','number'],['action','Action','text'],['module','Module','text'],['timestamp','Timestamp','datetime-local'],['ipAddress','IP address','text']], columns: ['id','userId','action','module','timestamp','ipAddress'] },
  allotments: { label: 'Room Allotments', icon: '⌂', endpoint: '/allotments', fields: [['resource','Room/Lab','entity-select','/resources'],['course','Course','entity-select','/courses'],['department','Department','text'],['section','Section','text'],['dayOfWeek','Day of Week','select',['MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY']],['startTime','Start Time','time'],['endTime','End Time','time']], columns: ['id','resource','course','department','section','dayOfWeek','startTime','endTime'] },
  hostels: { label: 'Hostels', icon: '🏨', endpoint: '/hostels', fields: [['name','Name','text'],['location','Location','text'],['type','Type','select',['BOYS','GIRLS']]], columns: ['id','name','location','type'] },
  rooms: { label: 'Rooms', icon: '🚪', endpoint: '/rooms', fields: [['roomNumber','Room Number','text'],['capacity','Capacity','number'],['currentOccupancy','Occupancy','number'],['type','Type','select',['AC','NON_AC']],['hostel','Hostel','entity-select','/hostels']], columns: ['id','roomNumber','capacity','currentOccupancy','type'] },
  roomAllocations: { label: 'Room Allocations', icon: '🔑', endpoint: '/room-allocations', fields: [['student','Student','entity-select','/students'],['room','Room','entity-select','/rooms'],['allocationDate','Date','date'],['status','Status','select',['ACTIVE','INACTIVE']]], columns: ['id','student','room','allocationDate','status'] },
  menuItems: { label: 'Canteen Menu', icon: '🍔', endpoint: '/menu-items', fields: [['name','Item Name','text'],['category','Category','select',['Breakfast','Lunch','Dinner','Snacks']],['price','Price','number'],['available','Available','select',['true','false']]], columns: ['id','name','category','price','available'] },
  canteenOrders: { label: 'Canteen Orders', icon: '📝', endpoint: '/canteen-orders', fields: [['student','Student','entity-select','/students'],['totalAmount','Total Amount','number'],['orderDate','Order Date','datetime-local'],['status','Status','select',['PENDING','COMPLETED','CANCELLED']]], columns: ['id','student','totalAmount','orderDate','status'] },
  vehicles: { label: 'Vehicles', icon: '🚌', endpoint: '/vehicles', fields: [['vehicleNumber','Vehicle No','text'],['type','Type','select',['BUS','CAR','VAN']],['capacity','Capacity','number'],['status','Status','select',['AVAILABLE','IN_USE']]], columns: ['id','vehicleNumber','type','capacity','status'] },
  travelRequests: { label: 'Travel Requests', icon: '🗺️', endpoint: '/travel-requests', fields: [['user','Requester','entity-select','/users'],['destination','Destination','text'],['purpose','Purpose','text'],['startDate','Start Date','datetime-local'],['endDate','End Date','datetime-local'],['status','Status','select',['PENDING','APPROVED','REJECTED']],['estimatedCost','Est. Cost','number']], columns: ['id','user','destination','startDate','endDate','status'] },
  travelAllocations: { label: 'Travel Allocations', icon: '🔑', endpoint: '/travel-allocations', fields: [['request','Request','entity-select','/travel-requests'],['vehicle','Vehicle','entity-select','/vehicles'],['allocationDate','Allocated Date','datetime-local']], columns: ['id','request','vehicle','allocationDate'] },
};const isAdminPanel = window.location.pathname.endsWith('/admin.html');
const isStudentPanel = window.location.pathname.endsWith('/student.html');
const isLibrarianPanel = window.location.pathname.endsWith('/librarian.html');
const isPlacementPanel = window.location.pathname.endsWith('/placement.html');
const studentModules = new Set(['students', 'courses', 'notices', 'events', 'books', 'issues', 'bookings']);
const librarianModules = new Set(['bookings', 'books', 'issues', 'librarians']);
const placementModules = new Set(['students', 'notices', 'events']);
const state = { auth: sessionStorage.getItem('campusAuth') || '', username: sessionStorage.getItem('campusUser') || '', active: 'dashboard', records: {}, editing: null };
const $ = (selector) => document.querySelector(selector);
const titleize = (value) => value === 'coverImagePath' ? 'Front Page' : value.replace(/([A-Z])/g, ' $1').replace(/^./, (c) => c.toUpperCase());
const singular = (label) => label === 'Faculty' ? 'Faculty' : label.slice(0, -1).replace('ie', 'y');

function authHeaders(json = false) { return { ...(json ? {'Content-Type':'application/json'} : {}), Authorization: `Basic ${state.auth}` }; }
async function api(path, options = {}) {
  const response = await fetch(path, { ...options, headers: { ...authHeaders(Boolean(options.body)), ...(options.headers || {}) } });
  if (!response.ok) {
    const message = await response.text();
    const err = new Error(message || `Request failed (${response.status})`);
    err.status = response.status;
    throw err;
  }
  const type = response.headers.get('content-type') || '';
  return type.includes('application/json') ? response.json() : response.text();
}
function message(text, isError = false) { const toast = $('#toast'); toast.textContent = text; toast.style.background = isError ? '#8d2e2e' : '#183e33'; toast.classList.remove('hidden'); setTimeout(() => toast.classList.add('hidden'), 3600); }
function setConnection(ok) { $('#connection-dot').classList.toggle('offline', !ok); $('#connection-label').textContent = ok ? 'API connected' : 'API unavailable'; }
function renderNav() { const visibleModules = Object.entries(modules).filter(([key]) => !isStudentPanel || studentModules.has(key)).filter(([key]) => !isLibrarianPanel || librarianModules.has(key)).filter(([key]) => !isPlacementPanel || placementModules.has(key)); const adminNavOrder = ['users','students','faculties','courses','notices','events','resources','allotments','bookings','books','issues','librarians','logs']; if (isAdminPanel) visibleModules.sort(([firstKey], [secondKey]) => adminNavOrder.indexOf(firstKey) - adminNavOrder.indexOf(secondKey)); $('#nav').innerHTML = `<button class="nav-item ${state.active === 'dashboard' ? 'active' : ''}" data-view="dashboard"><span class="nav-icon">⌂</span>Dashboard</button>${visibleModules.map(([key, mod]) => `<button class="nav-item ${state.active === key ? 'active' : ''}" data-view="${key}"><span class="nav-icon">${mod.icon}</span>${mod.label}</button>`).join('')}`; $('#nav').querySelectorAll('[data-view]').forEach((button) => button.addEventListener('click', () => navigate(button.dataset.view))); }
function toInputDate(value, type) { if (!value) return ''; if (type === 'datetime-local') return String(value).slice(0, 16); return String(value).slice(0, 10); }
function display(value) {
  if (value === null || value === undefined || value === '') return '—';
  if (typeof value === 'object') return value.name || value.title || JSON.stringify(value);
  if (typeof value === 'string') {
    if (/\d{4}-\d\d-\d\dT/.test(value)) return new Date(value).toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' });
    if (/^\d{2}:\d{2}(:\d{2})?$/.test(value)) {
      const [h, m] = value.split(':');
      let hour = parseInt(h);
      const ampm = hour >= 12 ? 'PM' : 'AM';
      hour = hour % 12 || 12;
      return `${hour}:${m} ${ampm}`;
    }
  }
  return String(value);
}
function isPlacementNotice(notice) { return /\b(placement|training)\b/i.test(notice?.issuedForRoles || ''); }

async function navigate(view) {
  state.active = view; $('#page-title').textContent = view === 'dashboard' ? 'Dashboard' : modules[view].label; $('#breadcrumb').textContent = view === 'dashboard' ? 'OVERVIEW' : 'CAMPUS DATA'; renderNav(); $('.sidebar').classList.remove('open');
  if (view === 'dashboard') return renderDashboard();
  return renderModule(view);
}
async function getRecords(key) { try { const data = await api(modules[key].endpoint); state.records[key] = Array.isArray(data) ? data : []; return state.records[key]; } catch (error) { state.records[key] = []; return []; } }
async function renderDashboard() {
  $('#content').innerHTML = `<p class="empty">Loading your campus overview…</p>`;
  const prominent = isLibrarianPanel ? ['bookings','books','issues','librarians'] : isPlacementPanel ? ['students','notices','events'] : isStudentPanel ? ['students','courses','bookings','notices','events','books','issues'] : ['students','courses','resources','allotments','bookings','notices','events','books','issues'];
  const accessPath = isLibrarianPanel ? '/librarian-panel/access' : isPlacementPanel ? '/placement-panel/access' : isStudentPanel ? '/student-panel/access' : '/dashboard';
  const [dashboardStatus, cacheStatus] = await Promise.all([api(accessPath).catch(() => null), api('/cache/ping').catch(() => null), ...prominent.map(getRecords)]); setConnection(Boolean(dashboardStatus));
  const total = (key) => state.records[key]?.length || 0;
  const librarianOverview = `<section class="library-welcome"><div><p class="eyebrow">LIBRARY WORKSPACE</p><h2>Good morning, ${escapeHtml(state.username || 'librarian')}.</h2><p>Keep the collection circulating with a quick view of today’s library activity.</p></div><div class="library-actions"><button class="library-action" data-go="issues"><span>↗</span> Record issue</button><button class="library-action secondary-action" data-go="books"><span>+</span> Add book</button></div></section><h2 class="overview-title">Collection at a glance</h2><div class="stats"><div class="stat-card"><div class="stat-label">Book bookings</div><div class="stat-value">${total('bookings')}</div></div><div class="stat-card"><div class="stat-label">Library books</div><div class="stat-value">${total('books')}</div></div><div class="stat-card"><div class="stat-label">Issued books</div><div class="stat-value">${total('issues')}</div></div><div class="stat-card"><div class="stat-label">Librarians</div><div class="stat-value">${total('librarians')}</div></div></div><div class="section-grid"><section class="panel"><div class="panel-head"><div><h2>Recent book bookings</h2><p class="panel-subtitle">Reservations waiting for review</p></div><button class="text-button" data-go="bookings">View all</button></div>${listCards(state.records.bookings, 'bookId', 'status', 'No book bookings have been made yet.')}</section><section class="panel"><div class="panel-head"><div><h2>Recent issues</h2><p class="panel-subtitle">Books currently recorded as issued</p></div><button class="text-button" data-go="issues">View all</button></div>${listCards(state.records.issues, 'bookId', 'dueDate', 'No book issues have been recorded yet.')}</section></div>`;
  const placementNotices = (state.records.notices || []).filter(isPlacementNotice);
  const placementOverview = `<h2 class="overview-title">Training &amp; Placement overview</h2><div class="stats"><div class="stat-card"><div class="stat-label">Registered students</div><div class="stat-value">${total('students')}</div></div></div><div class="section-grid"><section class="panel"><div class="panel-head"><div><h2>Student notices</h2><p class="panel-subtitle">Latest communication for students</p></div><button class="text-button" data-go="notices">View all</button></div>${listCards(placementNotices, 'title', 'body', 'No placement notices have been published yet.')}</section><section class="panel"><div class="panel-head"><div><h2>Placement events</h2><p class="panel-subtitle">Upcoming training and recruitment activities</p></div><button class="text-button" data-go="events">View all</button></div>${listCards(state.records.events, 'title', 'venue', 'No placement events are scheduled yet.')}</section><section class="panel"><div class="panel-head"><div><h2>System status</h2><p class="panel-subtitle">Live backend checks</p></div></div><div class="record-list"><article class="mini-record"><h3>${dashboardStatus ? 'Dashboard API connected' : 'Dashboard API unavailable'}</h3><p>${escapeHtml(dashboardStatus || 'Check that the Spring Boot service is running.')}</p></article><article class="mini-record"><h3>${cacheStatus ? 'Cache service responding' : 'Cache status unavailable'}</h3><p>${escapeHtml(cacheStatus || 'The cache ping endpoint did not respond.')}</p></article></div></section></div>`;
  const standardOverview = `<h2 class="overview-title">Today at a glance</h2><div class="stats"><div class="stat-card"><div class="stat-label">Enrolled students</div><div class="stat-value">${total('students')}</div></div><div class="stat-card"><div class="stat-label">Active courses</div><div class="stat-value">${total('courses')}</div></div>${isStudentPanel ? '' : `<div class="stat-card"><div class="stat-label">Campus resources</div><div class="stat-value">${total('resources')}</div></div>`}<div class="stat-card"><div class="stat-label">Book bookings</div><div class="stat-value">${total('bookings')}</div></div></div><div class="section-grid"><section class="panel"><div class="panel-head"><div><h2>Latest notices</h2><p class="panel-subtitle">Communication across campus</p></div><button class="text-button" data-go="notices">View all</button></div>${listCards(state.records.notices, 'title', 'body', 'No notices have been published yet.')}</section><section class="panel"><div class="panel-head"><div><h2>Upcoming events</h2><p class="panel-subtitle">What is happening next</p></div><button class="text-button" data-go="events">View all</button></div>${listCards(state.records.events, 'title', 'venue', 'No events are scheduled yet.')}</section><section class="panel"><div class="panel-head"><div><h2>System status</h2><p class="panel-subtitle">Live backend checks</p></div></div><div class="record-list"><article class="mini-record"><h3>${dashboardStatus ? 'Dashboard API connected' : 'Dashboard API unavailable'}</h3><p>${escapeHtml(dashboardStatus || 'Check that the Spring Boot service is running.')}</p></article><article class="mini-record"><h3>${cacheStatus ? 'Cache service responding' : 'Cache status unavailable'}</h3><p>${escapeHtml(cacheStatus || 'The cache ping endpoint did not respond.')}</p></article></div></section></div>`;
  $('#content').innerHTML = isLibrarianPanel ? librarianOverview : isPlacementPanel ? placementOverview : standardOverview;
  $('#content').querySelectorAll('[data-go]').forEach(button => button.addEventListener('click', () => navigate(button.dataset.go)));
}
function listCards(records, heading, subheading, empty) { if (!records?.length) return `<p class="empty">${empty}</p>`; return `<div class="record-list">${records.slice(0,4).map(row => `<article class="mini-record"><h3>${escapeHtml(display(row[heading]))}</h3><p>${escapeHtml(display(row[subheading]))}</p></article>`).join('')}</div>`; }
async function renderModule(key) {
  const mod = modules[key]; $('#content').innerHTML = `<p class="empty">Loading ${mod.label.toLowerCase()}…</p>`;
  const records = await getRecords(key); setConnection(true);
  const visibleRecords = isPlacementPanel && key === 'notices' ? records.filter(isPlacementNotice) : records;
  const columns = isStudentPanel && key === 'books' ? mod.columns.filter(column => column !== 'driveLink') : mod.columns;
  const canCreate = isLibrarianPanel ? key === 'books' || key === 'issues' : isPlacementPanel ? key === 'events' || key === 'notices' : !isStudentPanel || key === 'bookings';
  const canManage = isLibrarianPanel ? key === 'books' || key === 'issues' : isPlacementPanel ? key === 'events' || key === 'notices' : !isStudentPanel;
  $('#content').innerHTML = `<div class="module-head"><div class="module-copy"><h2>${mod.label}</h2><p>${visibleRecords.length} record${visibleRecords.length === 1 ? '' : 's'} from ${mod.endpoint}</p></div>${canCreate ? `<button class="primary" data-new="${key}">+ Add ${singular(mod.label)}</button>` : ''}</div><div class="table-shell">${visibleRecords.length ? `<table class="data-table"><thead><tr>${columns.map(col => `<th>${titleize(col)}</th>`).join('')}${canManage ? '<th>Actions</th>' : ''}</tr></thead><tbody>${visibleRecords.map(row => `<tr>${columns.map(col => `<td>${col === 'attachmentName' && row[col] ? `<button class="text-button" data-download="${row.id}">${escapeHtml(row[col])}</button>` : col === 'coverImagePath' && row[col] ? `<img data-cover="${row.id}" alt="Book cover" style="width:40px;height:56px;object-fit:cover;border-radius:4px" />` : col === 'driveLink' && row[col] ? `<a href="${escapeHtml(row[col])}" target="_blank" rel="noopener">Open link</a>` : escapeHtml(display(row[col]))}</td>`).join('')}${canManage ? `<td class="action-cell"><button data-edit="${row.id}">Edit</button><button class="delete" data-delete="${row.id}">Delete</button></td>` : ''}</tr>`).join('')}</tbody></table>` : `<p class="empty" style="padding:32px">No ${mod.label.toLowerCase()} available.</p>`}</div>`;
  $('[data-new]')?.addEventListener('click', () => openForm(key)); $('#content').querySelectorAll('[data-edit]').forEach(button => button.addEventListener('click', () => openForm(key, state.records[key].find(row => String(row.id) === button.dataset.edit)))); $('#content').querySelectorAll('[data-delete]').forEach(button => button.addEventListener('click', () => deleteRecord(key, button.dataset.delete))); $('#content').querySelectorAll('[data-download]').forEach(button => button.addEventListener('click', () => downloadAttachment(key, button.dataset.download))); $('#content').querySelectorAll('[data-cover]').forEach(image => loadCover(image, image.dataset.cover));
}
function escapeHtml(value) { const element = document.createElement('span'); element.textContent = value; return element.innerHTML; }
async function openForm(key, record = null) {
  const mod = modules[key]; state.editing = { key, record }; $('#modal-kicker').textContent = record ? 'EDIT RECORD' : 'NEW RECORD'; $('#modal-title').textContent = `${record ? 'Edit' : 'Add'} ${singular(mod.label)}`; $('#form-error').textContent = '';
  const formFields = !record && key === 'bookings' ? mod.fields.filter(([name]) => name !== 'endTime') : mod.fields;

  const fieldsHtml = await Promise.all(formFields.map(async ([name, label, type, options]) => {
    if (type === 'entity-select') {
      const endpoint = options;
      const data = await api(endpoint).catch(() => []);
      const optionsHtml = data.map(item => `<option value="${item.id}" ${record?.[name]?.id === item.id ? 'selected' : ''}>${item.name}</option>`).join('');
      return `<label>${label}<select name="${name}" required><option value="">Choose ${label.toLowerCase()}</option>${optionsHtml}</select></label>`;
    }
    if (type === 'textarea') return `<label class="field-wide">${label}<textarea name="${name}" ${name === 'password' && record ? '' : 'required'}>${record?.[name] || ''}</textarea></label>`;
    if (type === 'select') return `<label>${label}<select name="${name}" required><option value="">Choose ${label.toLowerCase()}</option>${options.map(item => `<option value="${item}" ${record?.[name] === item ? 'selected' : ''}>${item}</option>`).join('')}</select></label>`;
    if (type === 'file') return `<label>${label}<input name="${name}" type="file" accept="application/pdf" /></label>`;
    if (type === 'image') return `<label>${label}<input name="${name}" type="file" accept="image/png,image/jpeg,image/webp" /></label>`;
    if (type === 'readonly') return `<label>${label}<input name="${name}" value="${record?.[name] ?? 'Auto-generated after saving'}" readonly /></label>`;
    if (type === 'time') return `<label>${label}<input name="${name}" type="time" value="${record?.[name] ?? ''}" required /></label>`;
    return `<label>${label}<input name="${name}" type="${type}" value="${(type.includes('date')) ? toInputDate(record?.[name], type) : (record?.[name] ?? '')}" ${name === 'password' && record ? '' : 'required'} /></label>`;
  }));

  let finalHtml = fieldsHtml.join('');

  if (key === 'allotments') {
    // Group fields into logical sections for better UI
    const sections = {
      'Assignment': ['resource', 'course'],
      'Organization': ['department', 'section'],
      'Schedule': ['dayOfWeek', 'startTime', 'endTime']
    };

    let groupedHtml = '';
    for (const [sectionName, fieldNames] of Object.entries(sections)) {
      const sectionFields = fieldsHtml.filter((html, idx) => {
          const fieldName = mod.fields[idx][0];
          return fieldNames.includes(fieldName);
      }).join('');

      groupedHtml += `<div style="grid-column: 1/-1; margin-top: 20px; margin-bottom: 8px; font-size: 12px; font-weight: 800; color: var(--green); text-transform: uppercase; letter-spacing: .05em; border-bottom: 1px solid var(--line); padding-bottom: 4px;">${sectionName}</div>${sectionFields}`;
    }
    finalHtml = groupedHtml;
  }

  $('#form-fields').innerHTML = finalHtml; $('#modal').classList.remove('hidden');
}
function closeForm() { $('#modal').classList.add('hidden'); state.editing = null; }
async function saveRecord(event) { event.preventDefault(); const { key, record } = state.editing; const mod = modules[key]; const formData = new FormData(event.target); const payload = {};
  mod.fields.forEach(([name, , type]) => { const value = formData.get(name); if (type !== 'file' && type !== 'image' && type !== 'readonly' && value !== '' && value !== null) { if (type === 'entity-select') payload[name] = { id: Number(value) }; else payload[name] = type === 'number' ? Number(value) : value; } });
  try { const saved = await api(`${mod.endpoint}${record ? `/${record.id}` : ''}`, { method: record ? 'PUT' : 'POST', body: JSON.stringify(payload) }); const attachment = formData.get('attachment'); const cover = formData.get('cover'); if (attachment instanceof File && attachment.size) await uploadAttachment(mod.endpoint, saved.id, attachment); if (cover instanceof File && cover.size) await uploadCover(saved.id, cover); closeForm(); message(`${singular(mod.label)} saved.`); renderModule(key); } catch (error) { const msg = error.status === 409 ? 'Scheduling conflict: This room is already allotted for the selected time.' : `Unable to save: ${error.message}`; $('#form-error').textContent = msg; }
}
async function uploadAttachment(endpoint, id, file) { const formData = new FormData(); formData.append('file', file); const response = await fetch(`${endpoint}/${id}/attachment`, { method: 'POST', headers: authHeaders(), body: formData }); if (!response.ok) throw new Error((await response.text()) || `Upload failed (${response.status})`); }
async function uploadCover(id, file) { const formData = new FormData(); formData.append('file', file); const response = await fetch(`/books/${id}/cover`, { method: 'POST', headers: authHeaders(), body: formData }); if (!response.ok) throw new Error((await response.text()) || `Cover upload failed (${response.status})`); }
async function loadCover(image, id) { try { const response = await fetch(`/books/${id}/cover`, { headers: authHeaders() }); if (!response.ok) return; image.src = URL.createObjectURL(await response.blob()); } catch (_) { image.remove(); } }
async function downloadAttachment(key, id) { try { const response = await fetch(`${modules[key].endpoint}/${id}/attachment`, { headers: authHeaders() }); if (!response.ok) throw new Error(`Download failed (${response.status})`); const file = await response.blob(); const url = URL.createObjectURL(file); const link = document.createElement('a'); link.href = url; link.download = ''; link.click(); URL.revokeObjectURL(url); } catch (error) { message(error.message, true); } }
async function deleteRecord(key, id) { const mod = modules[key]; if (!confirm(`Delete this ${singular(mod.label).toLowerCase()}?`)) return; try { await api(`${mod.endpoint}/${id}`, { method: 'DELETE' }); message('Record deleted.'); renderModule(key); } catch (error) { message(`Unable to delete: ${error.message}`, true); } }
async function login(event) { event.preventDefault(); const username = $('#username').value.trim(), password = $('#password').value; state.auth = btoa(`${username}:${password}`); state.username = username; $('#login-error').textContent = '';
  const accessPath = isLibrarianPanel ? '/librarian-panel/access' : isPlacementPanel ? '/placement-panel/access' : isStudentPanel ? '/student-panel/access' : '/dashboard';
  try { await api(accessPath); sessionStorage.setItem('campusAuth', state.auth); sessionStorage.setItem('campusUser', username); $('#login-screen').classList.add('hidden'); $('#app').classList.remove('hidden'); $('#user-pill').textContent = username; navigate('dashboard'); } catch (error) { $('#login-error').textContent = isLibrarianPanel ? 'Librarian sign-in failed. Use a librarian account.' : isStudentPanel ? 'Student sign-in failed. Use a student account.' : 'Sign-in failed. Check the username and password.'; }
}
function logout() { sessionStorage.removeItem('campusAuth'); sessionStorage.removeItem('campusUser'); state.auth = ''; $('#app').classList.add('hidden'); $('#login-screen').classList.remove('hidden'); }
$('#login-form').addEventListener('submit', login); $('#record-form').addEventListener('submit', saveRecord); $('#close-modal').addEventListener('click', closeForm); $('#modal').addEventListener('click', event => { if (event.target.id === 'modal') closeForm(); }); $('#logout-button').addEventListener('click', logout); $('#menu-button').addEventListener('click', () => $('.sidebar').classList.toggle('open'));
if (state.auth) {
  const openApp = () => { $('#login-screen').classList.add('hidden'); $('#app').classList.remove('hidden'); $('#user-pill').textContent = state.username; navigate('dashboard'); };
  if (isAdminPanel) api('/dashboard').then(openApp).catch(logout); else if (isStudentPanel) api('/student-panel/access').then(openApp).catch(logout); else if (isLibrarianPanel) api('/librarian-panel/access').then(openApp).catch(logout); else if (isPlacementPanel) api('/placement-panel/access').then(openApp).catch(logout); else openApp();
}
