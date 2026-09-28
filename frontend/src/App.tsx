import { useEffect, useState } from "react";
import "./App.css";

type WorkOrder = {
  id: number;
  title: string;
  description: string;
  status: string;
  assignedTo: string;
};

type WorkOrderForm = {
  title: string;
  description: string;
  status: string;
  assignedTo: string;
};

const API_URL = "http://localhost:8080/api/work-orders";

const emptyForm: WorkOrderForm = {
  title: "",
  description: "",
  status: "OPEN",
  assignedTo: "",
};

function App() {
  const [workOrders, setWorkOrders] = useState<WorkOrder[]>([]);
  const [form, setForm] = useState<WorkOrderForm>(emptyForm);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [error, setError] = useState("");

  async function loadWorkOrders() {
    try {
      setError("");

      const response = await fetch(API_URL);

      if (!response.ok) {
        throw new Error("Failed to load work orders");
      }

      const data: WorkOrder[] = await response.json();
      setWorkOrders(data);
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Unable to load work orders"
      );
    }
  }

  useEffect(() => {
    const load = async () => {
      try {
        const response = await fetch(API_URL);

        if (!response.ok) {
          throw new Error("Failed to load work orders");
        }

        const data: WorkOrder[] = await response.json();
        setWorkOrders(data);
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "Unable to load work orders"
        );
      }
    };

    void load();
  }, []);

  function handleChange(
    event: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) {
    const { name, value } = event.target;

    setForm((previous) => ({
      ...previous,
      [name]: value,
    }));
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    try {
      setError("");

      const url =
        editingId !== null
          ? `${API_URL}/${editingId}`
          : API_URL;

      const response = await fetch(url, {
        method: editingId !== null ? "PUT" : "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(form),
      });

      if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Unable to save work order");
      }

      setForm(emptyForm);
      setEditingId(null);

      await loadWorkOrders();
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Unable to save work order"
      );
    }
  }

  function handleEdit(workOrder: WorkOrder) {
    setEditingId(workOrder.id);

    setForm({
      title: workOrder.title,
      description: workOrder.description,
      status: workOrder.status,
      assignedTo: workOrder.assignedTo,
    });
  }

  function cancelEdit() {
    setEditingId(null);
    setForm(emptyForm);
  }

  async function handleDelete(id: number) {
  const confirmed = window.confirm(
    "Are you sure you want to delete this work order?"
  );

  if (!confirmed) {
    return;
  }

  try {
    setError("");

    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error("Unable to delete work order");
    }

    await loadWorkOrders();
  } catch (err) {
    setError(
      err instanceof Error ? err.message : "Unable to delete work order"
    );
  }
}

  return (
    <main className="page">
      <div className="container">
        <h1>Work Order Tracker</h1>

        <section className="card">
          <h2>{editingId !== null ? "Update Work Order" : "Create Work Order"}</h2>

          <form onSubmit={handleSubmit}>
            <label>
              Title
              <input
                name="title"
                value={form.title}
                onChange={handleChange}
                required
              />
            </label>

            <label>
              Description
              <textarea
                name="description"
                value={form.description}
                onChange={handleChange}
                required
              />
            </label>

            <label>
              Status
              <select
                name="status"
                value={form.status}
                onChange={handleChange}
              >
                <option value="OPEN">Open</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="COMPLETED">Completed</option>
              </select>
            </label>

            <label>
              Assigned To
              <input
                name="assignedTo"
                value={form.assignedTo}
                onChange={handleChange}
                required
              />
            </label>

            <button type="submit">
              {editingId !== null ? "Update Work Order" : "Create Work Order"}
            </button>

            {editingId !== null && (
              <button type="button" onClick={cancelEdit}>
                Cancel
              </button>
            )}
          </form>

          {error && <p>{error}</p>}
        </section>

        <section className="card">
          <h2>Work Orders</h2>

          {workOrders.length === 0 ? (
            <p>No work orders yet.</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Title</th>
                  <th>Description</th>
                  <th>Status</th>
                  <th>Assigned To</th>
                  <th>Action</th>
                </tr>
              </thead>

              <tbody>
                {workOrders.map((workOrder) => (
                  <tr key={workOrder.id}>
                    <td>{workOrder.id}</td>
                    <td>{workOrder.title}</td>
                    <td>{workOrder.description}</td>
                    <td>{workOrder.status}</td>
                    <td>{workOrder.assignedTo}</td>
                    <td>
                      <td className="action-buttons">
                        <button onClick={() => handleEdit(workOrder)}>
                          Edit
                        </button>

                        <button
                          className="delete-button"
                          onClick={() => handleDelete(workOrder.id)}
                        >
                          Delete
                        </button>
                      </td>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      </div>
    </main>
  );
}

export default App;