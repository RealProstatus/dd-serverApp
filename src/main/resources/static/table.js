"strict use"
import { fetchTelemetryData } from "./api.js";

let telemetryChart;
let pollingInterval;
const maxDataPoints = 10; // Максимальное количество точек на графике

// Элементы управления
const startBtn = document.getElementById('startBtn');
const stopBtn = document.getElementById('stopBtn');
const intervalSelect = document.getElementById('intervalSelect');

// Инициализация графика
function initChart() {
    const ctx = document.getElementById('waterLevelChart').getContext('2d');
    
    telemetryChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'Telemtry',
                data: [],
                borderColor: 'rgb(75, 192, 192)',
                backgroundColor: 'rgba(75, 192, 192, 0.1)',
                borderWidth: 2,
                pointRadius: 3, // Включаем точки
                pointHoverRadius: 5, // Увеличиваем при наведении
                fill: true,
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            interaction: {
                intersect: false,
                mode: 'index'
            },
            animation: {
                duration: 500 // Плавная анимация обновления
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Время'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Значение'
                    }
                }
            }
        },
        tooltip: {
            enabled: true,
            mode: 'index',
            callbacks: {
                label: function(context) {
                    return `${context.dataset.label}: ${context.parsed.y.toFixed(2)}`;
                },
                title: function(context) {
                    return `Время: ${context[0].label}`;
                }
            },
            backgroundColor: 'rgba(0, 0, 0, 0.7)',
            titleFont: {
                size: 14,
                weight: 'bold'
            },
            bodyFont: {
                size: 12
            }
        },
        legend: {
            labels: {
                usePointStyle: true,
                pointStyle: 'circle'
            }
        }
    });
}

// Обновление графика новыми данными
function updateChart(newData) {
    if (!newData || !telemetryChart) return;
    console.log(newData);
    // Добавляем новые данные
    const timestamp = new Date().toLocaleTimeString();
    telemetryChart.data.labels.push(timestamp);
    console.log(newData[0].metricValue);
    telemetryChart.data.datasets[0].data.push(newData[0].metricValue);
    
    if (telemetryChart.data.labels.length > maxDataPoints) {
        telemetryChart.data.labels.shift();
        telemetryChart.data.datasets[0].data.shift();
    }
    
    // Обновляем график
    telemetryChart.update();
}

// Запуск поллинга
function startPolling() {
    const interval = parseInt(intervalSelect.value);
    
    // Останавливаем предыдущий интервал, если был
    if (pollingInterval) {
        clearInterval(pollingInterval);
    }
    
    // Сразу делаем первый запрос
    fetchTelemetryData()
        .then(data => {
            if (data) {
                updateChart(data);
                updateTable(data);
            }
        });

    // Устанавливаем интервал
    pollingInterval = setInterval(async () => {
        const data = await fetchTelemetryData();

        if (data) {
            updateChart(data);
            updateTable(data);
        }
    }, interval);
    
    console.log(`Поллинг запущен с интервалом ${interval} мс`);
}

// Остановка поллинга
function stopPolling() {
    if (pollingInterval) {
        clearInterval(pollingInterval);
        pollingInterval = null;
        console.log('Поллинг остановлен');
    }
}

// Обработчики событий
startBtn.addEventListener('click', startPolling);
stopBtn.addEventListener('click', stopPolling);

// Изменение интервала
intervalSelect.addEventListener('change', () => {
    if (pollingInterval) {
        startPolling(); // Перезапускаем с новым интервалом
    }
});
async function updateTable(data) {
    try {
        const tbody = document.getElementById('tableBody');
        tbody.innerHTML = ''; // Clear current rows
        
        if (data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No data available.</td></tr>';
        } else {
            data.forEach(item => {
                const row = document.createElement('tr');
                row.className = item.status === 'LOW_WATER' ? 'low-water' : 'normal-water';
                row.innerHTML = `
                    <td>${item.timestamp ? new Date(item.timestamp).toLocaleString() : 'N/A'}</td>
                    <td>${item.deviceId || 'N/A'}</td>
                    <td>${item.metricName || 'N/A'}</td>
                    <td>${item.metricValue + randomNumber || 'N/A'}</td>
                    <td>${item.status || 'N/A'}</td>
                `;
                tbody.appendChild(row);
            });
        }
    }catch (error) {
        console.error('Error updating table:', error);
        const tbody = document.getElementById('tableBody');
        tbody.innerHTML = '<tr><td colspan="5" class="text-center text-danger">Error loading data</td></tr>';
    }
}

window.addEventListener('DOMContentLoaded', () => {
    initChart();

});