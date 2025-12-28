const btnCheckBalance = document.getElementById('btnCheckBalance');
const inputAccId = document.getElementById('accId');
const balanceDisplay = document.getElementById('balanceValue');
const consoleBox = document.getElementById('console');
const amount = document.getElementById('depAmount');
const btnCreateAcc = document.getElementById('btnCreateAcc');
const btnAddBalance = document.getElementById('btnAddBalance');
const btnCreateOrder = document.getElementById('btnCreateOrder');
const orderUserId = document.getElementById('orderUserId');
const orderDesc = document.getElementById('orderDesc');
const orderAmt = document.getElementById('orderAmt');
const btnRefreshOrders = document.getElementById('btnRefreshOrders');
const orderTable = document.getElementById('orderTable');

const GATEWAY_URL = 'http://localhost:8080/api';

btnCheckBalance.addEventListener('click', async () => {
    const id = inputAccId.value;

    if (!id) {
        updateConsole("Ошибка: Введите ID аккаунта", true);
        return;
    }

    try {
        const response = await fetch(`${GATEWAY_URL}/accounts/${id}/balance`);

        if (!response.ok) {
            throw new Error(`Сервер вернул ошибку: ${response.status}`);
        }

        const data = await response.json();
        const finalBalance = (typeof data === 'object' && data !== null) ? (data.balance ?? data.amount ?? 0) : data;

        balanceDisplay.innerText = finalBalance || 0;
        updateConsole(`Баланс получен для ${id}.`);

    } catch (error) {
        updateConsole(`Ошибка: ${error.message}`, true);
    }
});

btnCreateAcc.addEventListener('click', async () => {
    updateConsole("Создание нового аккаунта...");
    try {
        let targetId = inputAccId.value.trim(); 

        if (!targetId) {
            targetId = crypto.randomUUID(); 
            inputAccId.value = targetId;
            updateConsole(`Сгенерирован UUID: ${targetId}`);
        }

        const finalUrl = new URL(`${GATEWAY_URL}/accounts/${targetId}/create`);

        const response = await fetch(finalUrl.href, { 
            method: 'POST' 
        });

        if (!response.ok) {
            throw new Error(`Сервер ответил: ${response.status}`);
        }

        let confirmedId = targetId;
        
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            const data = await response.json();
            confirmedId = data.id || data.userId || targetId;
        }

        orderUserId.value = confirmedId;
        updateConsole(`Аккаунт создан (ID: ${confirmedId})`);
        
        setTimeout(() => btnCheckBalance.click(), 200);

    } catch (error) {
        updateConsole(`Ошибка: ${error.message}`, true);
    }
});

btnAddBalance.addEventListener('click', async () => {
    const id = inputAccId.value.trim();
    const sum = amount.value.trim();

    if (!id || !sum) {
        updateConsole("Ошибка: Введите ID аккаунта и сумму пополнения", true);
        return;
    }

    updateConsole(`Пополнение аккаунта ${id} на ${sum} ₽`);

    try {
        const url = `${GATEWAY_URL}/accounts/${id}/deposit?amount=${sum}`;

        const response = await fetch(url, {
            method: 'POST'
        });

        if (!response.ok) {
            const errorMsg = await response.text();
            throw new Error(`Сервер ответил ${response.status}: ${errorMsg}`);
        }

        updateConsole(`Баланс пополнен на ${sum} ₽`);
        
        amount.value = '';

        btnCheckBalance.click();

    } catch (error) {
        updateConsole(`Ошибка пополнения: ${error.message}`, true);
    }
});

btnCreateOrder.addEventListener('click', async () => {
    const userId = orderUserId.value.trim();
    const description = orderDesc.value.trim();
    const amountValue = orderAmt.value;

    if (!userId || !amountValue) {
        updateConsole("Ошибка: Укажите ID пользователя и цену", true);
        return;
    }

    updateConsole(`Оформление заказа для ${userId}`);

    try {
        const response = await fetch(`${GATEWAY_URL}/orders`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId: userId,
                description: description || "Новый заказ",
                amount: parseFloat(amountValue)
            })
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Ошибка ${response.status}: ${errorText}`);
        }

        const result = await response.json();
        
        updateConsole(`Заказ успешно создан! ID заказа: ${result.id || 'OK'}`);
        
        orderDesc.value = '';
        orderAmt.value = '';

        setTimeout(() => btnCheckBalance.click(), 500);

    } catch (error) {
        updateConsole(`Не удалось создать заказ: ${error.message}`, true);
    }
});

btnRefreshOrders.addEventListener('click', async () => {
    const userId = orderUserId.value.trim();

    if (!userId) {
        updateConsole("Ошибка: Введите ID пользователя для загрузки истории", true);
        return;
    }

    updateConsole(`Загрузка заказов для ${userId}`);

    try {
        const response = await fetch(`${GATEWAY_URL}/orders/user/${userId}`);

        if (!response.ok) {
            throw new Error(`Ошибка ${response.status}: Не удалось загрузить список`);
        }

        const orders = await response.json();

        orderTable.innerHTML = '';

        if (orders.length === 0) {
            updateConsole("Заказов пока нет.");
            orderTable.innerHTML = '<tr><td colspan="4" style="text-align:center">История пуста</td></tr>';
            return;
        }

        orders.forEach(order => {
            const row = document.createElement('tr');

            let statusColor = '#666';
            if (order.status === 'PAID') statusColor = 'green';
            if (order.status === 'FAILED') statusColor = 'red';

            row.innerHTML = `
                <td>${order.id.slice(0, 8)}...</td>
                <td>${order.description || '—'}</td>
                <td>${order.amount} ₽</td>
                <td style="color: ${statusColor}; font-weight: bold;">${order.status}</td>
            `;
            orderTable.appendChild(row);
        });

        updateConsole(`Загружено заказов: ${orders.length}`);

    } catch (error) {
        updateConsole(`Ошибка загрузки: ${error.message}`, true);
    }
});

function updateConsole(message, isError = false) {
    const time = new Date().toLocaleTimeString();
    consoleBox.innerHTML += `<div style="color: ${isError ? 'red' : '#0f0'}">[${time}] ${message}</div>`;
    consoleBox.scrollTop = consoleBox.scrollHeight;
}