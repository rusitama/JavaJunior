package org.example;

class Cat extends Animal {
    private boolean hasTail;

    public Cat(String name, int age, boolean hasTail) {
        super(name, age);
        this.hasTail = hasTail;
    }

    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }

    public boolean hasTail() {
        return hasTail;
    }
}
