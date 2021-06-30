# PICKUP
## Epam external training final project
## Specification:

> Проект #15
> Система Прокат автомобилей. Клиент выбирает Автомобиль из списка доступных.
> Клиент заполняет форму заказа, указывая паспортные данные, срок аренды.
> Клиент оплачивает Заказ. Администратор регистрирует возврат автомобиля.
> В случае повреждения Автомобиля, Администратор вносит информацию и выставляет счёт за
> ремонт. Администратор может отклонить Заявку, указав причины отказа.

Besides epam specification I made additional requirements list :
- Customer can undo his order request in 'Profile' tab
- During making order request customer can check selected Car occupation
- During making order request customer can enter promocode (if exists)
- Rating system. Every customer has from 0 to 100 rate points. Most of customer's actions change customer rate (for example, downgrade in car accident). Every new customer has initial rate -  50 points
- Admin can check all active order request list and accept/reject its elements (with accept template/rejection reason email sending). For every order request admin can check its owner statistics
- Admin can add new car to catalog
- Admin can check all promocodes list, disable active promocode and add new promocode