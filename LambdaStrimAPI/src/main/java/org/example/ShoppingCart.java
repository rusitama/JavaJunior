package org.example;

import java.util.List;
import java.util.stream.Collectors;

// Задача Переработать метод балансировки корзины товаров cardBalancing() с использованием Stream API

public class ShoppingCart {

    // Метод для балансировки корзины товаров
    public static List<CartItem> balanceCart(List<CartItem> cart, double targetBalance) {
        // Сначала вычисляем текущий баланс корзины
        double currentBalance = cart.stream()
                .mapToDouble(CartItem::getPrice)
                .sum();

        // Сортируем корзину по убыванию цен товаров
        List<CartItem> sortedCart = cart.stream()
                .sorted((item1, item2) -> Double.compare(item2.getPrice(), item1.getPrice()))
                .collect(Collectors.toList());

        // Находим количество товаров, которые нужно удалить или добавить
        int numItemsToRemove = (int) ((currentBalance - targetBalance) / sortedCart.get(sortedCart.size() - 1).getPrice());
        int numItemsToAdd = -numItemsToRemove;

        // Фильтруем корзину, удаляя или добавляя товары, чтобы достигнуть целевого баланса
        return sortedCart.stream()
                .limit(sortedCart.size() - numItemsToRemove)  // Удаляем лишние товары
                .peek(item -> item.setPrice(targetBalance / (cart.size() + numItemsToAdd)))  // Обновляем цену оставшихся товаров
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        // Пример использования
        List<CartItem> cart = List.of(
                new CartItem("Товар 1", 10.0),
                new CartItem("Товар 2", 20.0),
                new CartItem("Товар 3", 30.0)
        );

        double targetBalance = 50.0;

        List<CartItem> balancedCart = balanceCart(cart, targetBalance);

        System.out.println("Балансировка корзины:");
        balancedCart.forEach(item -> System.out.println(item.getName() + ": " + item.getPrice()));
    }
}
