import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String filepath = "src/handbook.txt";
        HashMap<String, Float> handbook = new HashMap<String, Float>(); //создаем справочник товаров

        HashMapBook one = new HashMapBook();
        one.createHandbook(filepath, handbook);  //Создаем справочник товаров с помощью метода из HashMapBook

        ArrayList<String[]> basket = new ArrayList<>(); //Создаем динамческий список, уда будем класть данне корзины

        Date dateNow = new Date(); //Создаем текущую дату
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy"); //Приводим дату к виду

        //Открываем потом записи в файл
        try(FileWriter writer = new FileWriter("src/basket.txt", false)){

            writer.write("Hello, ShiftLab!");

            //Записываем заголовок, его же будем использовать в цикле пи выводе сообщения в консоль
            String basketdata = "\nЗаказ №12312 Фамилия Имя Отчество " + formatForDateNow.format(dateNow)+ "\n" + "\n"+
                    "Название   Цена    Количество  Сумма";
            writer.write(basketdata);

            String total =""; //Переменная для подведения итоговой суммы

            int i = 1;
            while (i != 0) {
                System.out.println("\nHello, ShiftLab!\nКогда закончите работу, напишите 'конец', чтобы записать корзину" +
                        " в файл\nВводите товар:"); //Краткое системное сообщение
                Scanner igotanswer = new Scanner(System.in);
                String data = igotanswer.nextLine(); //Вводим товар и преобразуем его в строку

                //Цикл для завершения работы программы
                if (data.equals("конец")) {
                    System.out.println("Работа с корзиной окончена.");
                    break;
                }

                try {

                    String [] first = data.split(" ", 2); //Разбиваем полученную строку на две части

                    //Если введенных частей больше двух, то выводим ошибку
                    if (first.length >= 2) {
                        String name = first[0]; //Приводим первое слово к строке, чтобы потом искать среди handbook
                        int count = 0;

                        //Пробуем привести вторую часть к числу, чтобы производить вычисления
                        try {
                            count = Integer.parseInt(first[1]);
                        } catch (NumberFormatException e) {
                            System.err.println("Неправильный формат введенной строки!");
                        }

                        //Проверяем наличие первой части в словаре
                        if (handbook.containsKey(first[0]))
                        {   //Слово окаалось в словаре, поэтому подтягиваем из словаря цену и производим умножение с округлением
                            String position4 = String.format("%.1f", count*handbook.get(name));
                            String position3 = Integer.toString(count);
                            String position2 = Float.toString(handbook.get(name));
                            String [] position = {first[0], position2, position3, position4};
                            basket.add(position); //Добавляем позицию в корзину


                            System.out.println(basketdata); //Выводим в консоль заголовок

                            //Цикл for для вывода содержимого корзины
                            float summary =0;
                            for (int k =0; k < basket.size(); k++) {
                                String [] newone = basket.get(k); //Конвертируем позицию из корзины в список

                                //Список приводим к строчному виду
                                String posInBasket = String.format("%s     %s     %s          %s",
                                        newone[0], newone[1], newone[2], newone[3]);
                                System.out.println(posInBasket); //Выводим строку-позицию из корзины

                                //Счетчик текущей корзины
                                summary += Float.parseFloat(newone[3].replace(",", "."));
                            }


                            total = "\nИтого: "+summary; //Добавляем к счетчику строку и выводим её
                            System.out.println(total);



                        }
                        else {
                            System.err.println("Данного товара нет в наличии.");
                        }
                    }
                    else {
                        System.err.println("Вы ввели данные с ошибкой.");
                    }


                } catch (ArrayIndexOutOfBoundsException e){

                    System.err.println("Вы ввели данные с ошибкой.");

                }




            } //Конец бесконечного цикла

            //Проходимся опять по содержимому корзины и записываем его в txt
            for (int k =0; k < basket.size(); k++) {
                String [] forTxt = basket.get(k);

                String posInBasket = String.format("%s     %s     %s          %s",
                        forTxt[0], forTxt[1], forTxt[2], forTxt[3]);
                writer.write("\n"+posInBasket);

            }

            writer.write("\n"+total); //Записываем итоговую сумму
            writer.flush(); //Закрываем поток


        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }

    }
}
