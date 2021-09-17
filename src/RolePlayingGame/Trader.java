package RolePlayingGame;

public class Trader implements Sale {
    // метод продажи
    @Override
    public String sell(Goods goods) {
        String result = "";
        if (goods == Goods.POTION) {
            result = "potion";
        }
        return result;
    }

    //Энам для товаров
    public enum Goods {
        POTION
    }
}
