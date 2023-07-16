package segundo.desafio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;

public class SecondChallenge {

    private static final Map<BigDecimal, Integer> moneyPerValueOfBillAndCoinMap = new HashMap<>();
    private static final SecondChallenge singletonInstance = new SecondChallenge();
    private static final String NOT_PARSABLE_STRING = "intentionalNotNumericString";
    private static final String BILLS_STRING = "nota";
    private static final String COINS_STRING = "moeda";

    private SecondChallenge() {
        moneyPerValueOfBillAndCoinMap.putAll(Map.of(
                BigDecimal.valueOf(10000L, 2), 0,
                BigDecimal.valueOf(5000L, 2), 0,
                BigDecimal.valueOf(2000L, 2), 0,
                BigDecimal.valueOf(1000L, 2), 0,
                BigDecimal.valueOf(500L, 2), 0,
                BigDecimal.valueOf(200L, 2), 0)
        );
        moneyPerValueOfBillAndCoinMap.putAll(Map.of(
                BigDecimal.valueOf(100L, 2), 0,
                BigDecimal.valueOf(100L / 2, 2), 0,
                BigDecimal.valueOf(100L / 4, 2), 0,
                BigDecimal.valueOf(100L / 10, 2), 0,
                BigDecimal.valueOf(100L / 20, 2), 0,
                BigDecimal.valueOf(100L / 100, 2), 0)
        );
    }

    public static void main(String[] args) {
        double selectedAmountOfMoney = -1d;
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Olá, bem vindo, vamos começar o desafio!");
        System.out.println("Para começar, por favor, escolha um valor monetário variando de R$ 0.00 até no máximo R$ 1000000.00 (com precisão de até duas casas decimais):");
        while (true) {
            if (selectedAmountOfMoney >= 0 && selectedAmountOfMoney <= 1e6)
                break;
            System.out.println();
            String userInput = userInputScanner.nextLine();
            System.out.println();
            if (userInput.trim().equalsIgnoreCase("s"))
                break;
            boolean isValidMonetaryValue = singletonInstance.isMonetaryValue(userInput);
            if (selectedAmountOfMoney < 0 && isValidMonetaryValue)
                selectedAmountOfMoney = Double.parseDouble(userInput);
            if (isValidMonetaryValue &&
                (selectedAmountOfMoney > 1e6 || selectedAmountOfMoney < 0 || (userInput.contains(".") && userInput.trim().substring(userInput.trim().indexOf(".")).length() > 3)) &&
                !singletonInstance.isMonetaryValue(NOT_PARSABLE_STRING))
                selectedAmountOfMoney = -1d;
        }
        if (selectedAmountOfMoney >= 0) {
            System.out.println("Pronto, segue a sequência das notas e das moedas (em ordem decrescente) minimamente necessárias para compor o valor monetário selecionado:");
            System.out.println();
            BigDecimal moneyAmountConvertedToBigDecimal = BigDecimal.valueOf(selectedAmountOfMoney * 100).divide(BigDecimal.valueOf(100L), 2, RoundingMode.DOWN);
            moneyPerValueOfBillAndCoinPrinter(
                    singletonInstance.moneyPerValueOfBillAndCoinSelector(
                        moneyAmountConvertedToBigDecimal
                    ),
                    BILLS_STRING,
                    (BigDecimal moneyType) -> moneyType.compareTo(BigDecimal.ONE) > 0
            );
            moneyPerValueOfBillAndCoinPrinter(
                    singletonInstance.moneyPerValueOfBillAndCoinSelector(
                        moneyAmountConvertedToBigDecimal
                    ),
                    COINS_STRING,
                    (BigDecimal moneyType) -> moneyType.compareTo(BigDecimal.ONE) <= 0
            );
        }
        System.out.println("Obrigado por participar do desafio #2! Volte sempre.");
        System.out.println();
        userInputScanner.close();
    }

    public static SecondChallenge getSingleton() {
        return singletonInstance;
    }

    private static void moneyPerValueOfBillAndCoinPrinter(Map<BigDecimal, Integer> monetaryValueCatalogedTable, String toUpperCaseString, Predicate<BigDecimal> moneyMapFilterCriteria) {
        System.out.println(toUpperCaseString.toUpperCase().concat("S:"));
        monetaryValueCatalogedTable.keySet()
            .stream()
            .sorted(Collections.reverseOrder())
            .filter(moneyMapFilterCriteria)
            .forEach(moneyType -> {
                int numberOfEachMoneyTypeRequired = monetaryValueCatalogedTable.get(moneyType);
                System.out.println(
                    (numberOfEachMoneyTypeRequired > 9999 ?
                    numberOfEachMoneyTypeRequired :
                    (numberOfEachMoneyTypeRequired >= 1000 ?
                    numberOfEachMoneyTypeRequired + " " :
                    (numberOfEachMoneyTypeRequired >= 100 ?
                    numberOfEachMoneyTypeRequired + "  " :
                    (numberOfEachMoneyTypeRequired >= 10 ?
                    numberOfEachMoneyTypeRequired + "   " :
                    numberOfEachMoneyTypeRequired + "    ")))) +
                    "     ".concat(toUpperCaseString.concat("(s)")) +
                    "     ".concat("de     R$") +
                    "     ".concat(moneyType.toString().length() > 5 ? "" + moneyType : (moneyType.toString().length() == 5 ? " " + moneyType : "  " + moneyType))
                );
            });
        System.out.println();
    }

    private boolean isMonetaryValue(String maybeDouble) {
        try {
            Double.parseDouble(maybeDouble);
            return true;
        } catch (NumberFormatException failedConversionToDouble) {
            System.out.println();
            System.out.println("Desculpe, valor inválido!");
            System.out.printf("Por favor, digite um valor apropriado para prosseguirmos ou digite %s para sair. ", "'s'");
            return false;
        }
    }

    public Map<BigDecimal, Integer> moneyPerValueOfBillAndCoinSelector(BigDecimal decimalValueSelected) {
        return decimalValueSelected.compareTo(BigDecimal.ZERO) == 0 ?
            moneyPerValueOfBillAndCoinMap :
            moneyPerValueOfBillAndCoinSelection(decimalValueSelected, new HashMap<>(moneyPerValueOfBillAndCoinMap));
    }

    private Map<BigDecimal, Integer> moneyPerValueOfBillAndCoinSelection(BigDecimal decimalValueSelected, Map<BigDecimal, Integer> copyOfClassMap) {
        BigDecimal copyOfSelectedValue = decimalValueSelected;
        for (BigDecimal moneyValueType:
                copyOfClassMap.keySet()
                        .stream()
                        .sorted(Collections.reverseOrder())
                        .toList()) {
            int integerPartOfDividedResult = numberOfTimesIntCanBeDividedByOther(copyOfSelectedValue, moneyValueType);
            copyOfClassMap.put(moneyValueType, integerPartOfDividedResult);
            copyOfSelectedValue = copyOfSelectedValue.subtract(moneyValueType.multiply(BigDecimal.valueOf(integerPartOfDividedResult)));
        }
        return copyOfClassMap;
    }

    private int numberOfTimesIntCanBeDividedByOther(BigDecimal dividedNumber, BigDecimal divisorNumber) {
        return dividedNumber.divide(divisorNumber, 0, RoundingMode.DOWN).intValue();
    }
}
