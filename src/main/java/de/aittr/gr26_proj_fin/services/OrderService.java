package de.aittr.gr26_proj_fin.services;

import de.aittr.gr26_proj_fin.domain.CommonBook;
import de.aittr.gr26_proj_fin.domain.CommonOrder;
import de.aittr.gr26_proj_fin.domain.CommonUser;
import de.aittr.gr26_proj_fin.repositories.interfaces.OrderRepository;
import de.aittr.gr26_proj_fin.repositories.interfaces.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Метод для генерации случайного числа в указанном диапазоне
    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public String addNewOrder(String userName) {
        CommonUser user = userRepository.findByname(userName);
        if (user != null) {
            Set<CommonOrder> orders = orderRepository.findAllByUser_Id(user.getId());//Находим сет заказов юзера
//        for (CommonOrder order : orders) {
//            int orderId = order.getId();
//        }
            long activeOrdersCount = orders.stream()//Находим количесвто не оплаченных заказов
                    .filter(CommonOrder::isIs_active)
                    .count();
            if (3 < activeOrdersCount) {//Если не оплаченных заказов больше 3-ех отказываем в оформлении последующих
                return "You have three unpaid orders! Please pay.";
            }
            CommonOrder order = new CommonOrder();
            int numberOfOrder = generateRandomNumber(100000, 999999);//Определяем номер нового заказа
            order.setId(0);
            order.setIs_active(true);//Ставим флаг не оплаченного заказа
            order.setNumber(numberOfOrder);
            //int maxId = orderRepository.findMaxId();
//            CommonUser user = userRepository.findById(userId).orElse(null);//Находим юзера по его id
//            if (user != null) {
                if (user.getCart().getBooks().isEmpty()) {//Если его корзина пустая - отказываем в оформлении заказа
                    return "Your cart is empty!";
                }
                List<CommonBook> books = user.getCart().getBooks();//Берем все книги из корзины юзера
                for (CommonBook book : books) {
                    order.addBook(book);//Добавляем все книги из корзины юзера в оформляемый заказ
                }
                order.setUser(user);
                user.addOrder(order);
                userRepository.save(user);
                return "Your order No. " + numberOfOrder + " has been accepted";
//            }
//            return "Unfortunately, your order No. " + numberOfOrder + " was not accepted for technical reasons.";
        }
        return "Unfortunately, a user with this login: " + userName + " was not found!";
    }

    public List<CommonOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public CommonOrder getOrderByNumber(Integer num) {
        return orderRepository.findByNumber(num);
    }

    public CommonOrder setMarkOfPaymentOfOrder(Integer number) {
        CommonOrder order = orderRepository.findByNumber(number);
        order.setIs_active(false);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public List<CommonOrder> delTheOrder(Integer num) {
        CommonOrder order = orderRepository.findByNumber(num);
        order.getBooks().clear();
        orderRepository.save(order);
        orderRepository.delete(order);
        return getAllOrders();
    }
}
