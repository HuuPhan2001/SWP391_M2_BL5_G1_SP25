<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Delete</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="modal show fade" id="confirmModal" tabindex="-1" style="display: block;" aria-modal="true" role="dialog">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalTitle">Confirm Delete</h5>
      </div>
      <div class="modal-body">
        <p id="modalMessage">Are you sure you want to delete this item?</p>
      </div>
      <div class="modal-footer">
        <form id="deleteForm" method="post">
            <input type="hidden" name="id" id="objectId" value="" />
            <button type="submit" class="btn btn-danger">Yes</button>
        </form>
        <button class="btn btn-secondary" onclick="parent.postMessage('close-modal', '*')">Cancel</button>
      </div>
    </div>
  </div>
</div>

<script>
    window.addEventListener("message", function(event) {
        if (event.data && typeof event.data === 'object') {
            const type = event.data.type || 'item';
            const id = event.data.id || '';

            document.getElementById('objectId').value = id;
            document.getElementById('modalMessage').innerText = `Are you sure you want to delete this ${type}?`;
            document.getElementById('modalTitle').innerText = `Confirm Delete ${type.charAt(0).toUpperCase() + type.slice(1)}`;

            const form = document.getElementById('deleteForm');
            form.action = `delete-${type}`;
        }
    });
</script>

</body>
</html>
