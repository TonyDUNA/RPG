package RolePlayingGame;

public class TheFight {
    //Метод, который вызывается при начале боя, сюда мы передаем ссылки на нашего героя и монстра, который встал у него на пути
    public void fight(Character hero, Character monster, TheWorld.FightCallback fightCallback) {
        //Ходы будут идти в отдельном потоке
        Runnable runnable = () -> {
            //Сюда будем записывать, какой сейчас ход по счету
            int turn = 1;
            //Когда бой будет закончен мы
            boolean isFightEnded = false;
            while (!isFightEnded) {
                System.out.println("----Ход: " + turn + "----");
                //Воины бьют по очереди, поэтому здесь мы описываем логику смены сторон
                if (turn++ % 2 != 0) {
                    isFightEnded = makeHit(monster, hero, fightCallback);
                } else {
                    isFightEnded = makeHit(hero, monster, fightCallback);
                }
                try {
                    //Чтобы бой не проходил за секунду, сделаем имитацию работы, как если бы
                    //у нас была анимация
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //Запускаем новый поток
        Thread thread = new Thread(runnable);
        thread.start();
    }
    //Метод для совершения удара
    private Boolean makeHit(Character defender, Character attacker, TheWorld.FightCallback fightCallback) {
        //Получаем силу удара
        int hit = attacker.attack();
        //Отнимаем количество урона из здоровья защищающегося
        int defenderHealth = defender.getHealth() - hit;
        //Если атака прошла, выводим в консоль сообщение об этом
        if (hit != 0) {
            System.out.println(String.format("%s Нанес удар в %d единиц!", attacker.getName(), hit));
            System.out.println(String.format("У %s осталось %d единиц здоровья...", defender.getName(), defenderHealth));
        } else {
            //Если атакующий промахнулся (то есть урон не 0), выводим это сообщение
            System.out.println(String.format("%s промахнулся!", attacker.getName()));
        }
        if (defenderHealth <= 0 && defender instanceof Hero) {
            //Если здоровье меньше 0 и если защищающейся был героем, то игра заканчивается
            System.out.println("Извините, вы пали в бою...");
            //Вызываем коллбэк, что мы проиграли
            fightCallback.fightLost();
            return true;
        } else if(defenderHealth <= 0) {
            //Если здоровья больше нет и защищающийся – это монстр, то мы забираем от монстра его опыт и золото
            System.out.println(String.format("Враг повержен! Вы получаете %d опыт и %d золота", defender.getPower(), defender.getGold()));
            attacker.setPower(attacker.getPower() + defender.getPower());
            attacker.setGold(attacker.getGold() + defender.getGold());
            //вызываем коллбэк, что мы победили
            fightCallback.fightWin();
            return true;
        } else {
            //если защищающийся не повержен, то мы устанавливаем ему новый уровень здоровья
            defender.setHealth(defenderHealth);
            return false;
        }
    }
}

//    public void fight(Character attacker, Character defender, FightCallback fightCallback) {
//        int hurt = 0; // hurt
//        int remainLife; // Оставшаяся жизнь
//        if (attacker.attack() > defender.attack()) {
//            hurt = attacker.attack() - defender.attack();
//        } else {
//            hurt = 0;
//        }
//        remainLife = defender.getHealth() - hurt;
//        System.out.println(defender.getName() + "получил " + hurt + " урон!");
//
//        if (remainLife <= 0) {
//            defender.setHealth(0);
//            System.out.println(defender.getName() + " убит");
//            fightCallback.fightWin();
//        } else {
//            defender.setHealth(remainLife);
//        }
//    }
//}
