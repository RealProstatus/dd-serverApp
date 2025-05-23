export async function fetchTelemetryData() {
    const res = await fetch('/dashboard/data')
    if (!res.ok) throw new Error('Network response was not ok');
    return await res.json();
}