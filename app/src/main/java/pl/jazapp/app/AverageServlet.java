package pl.jazapp.app;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@WebServlet("average")
public class AverageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
        resp.setContentType("text/plain");

        var writer = resp.getWriter();
        writer.println("Czesc Ty!");
        try {
            var getNumbers = req.getParameter("numbers"); //zmienna pobierająca dane wejściowe

            String[] numbers = getNumbers.split(","); //tworzymy tablicę, w której cyfry będą oddzielone przecinkami

            List<Integer> numbersList = new ArrayList<>(); //Utworzenie listy tablicowej

            for (String number : numbers) {
                numbersList.add(Integer.parseInt(number.trim())); //zmiana ciągów znaków na liczby rzeczywiste int
                                                                // number.trim pomija zbędne zera
            }

            OptionalDouble average = numbersList
                    .stream()
                    .mapToDouble(a -> a)
                    .average();
            //obliczenie średniej wartości z tablicy

            BigDecimal bdAverage = BigDecimal.valueOf(average.getAsDouble());
            BigDecimal result = bdAverage.round(new MathContext(3, RoundingMode.HALF_UP)).stripTrailingZeros();
            //ustawienie ilosci liczb znaczących w wyniku i usunięcie zer końcowych

            writer.println("Srednia podanych przez Ciebie liczb to: " + result);
        }
        catch (NullPointerException | NumberFormatException e) {
            writer.println("Prosze podac liczby");
            //Wyjątek, jeśli w adresie html nie zostaną podane żadne liczby
        }
    }
}
