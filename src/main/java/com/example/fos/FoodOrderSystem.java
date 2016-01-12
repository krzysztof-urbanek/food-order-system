package com.example.fos;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.example.fos.model.CourseType;
import com.example.fos.model.Cuisine;
import com.example.fos.model.order.DrinkOrder;
import com.example.fos.model.order.LunchOrder;
import com.example.fos.model.order.Order;
import com.example.fos.model.product.Dish;
import com.example.fos.model.product.Drink;

public class FoodOrderSystem {

	private static final String PRODUCT_PRESENTATION = "(%d). %s | Price: %s";

	private static final Locale currencyLocale = new Locale("pl", "PL");
	
	private static final String YES_PATTERN = "^([Yy](es)?).?$";
	
	private static final String NO_PATTERN = "^([Nn]o?).?$";
	
	private static final String NON_DIGIT_CHAR_PATTERN = "\\D+";

	private static final String NUMERIC_ANSWER_PATTERN = "^[(]?[0-9]+[)]?[.]?$";

	private static final String ORDER_LUNCH_OR_DRINK = 
			"What would you like to order? (Type corresponding number)\n(1). Order lunch.          (2). Order drink.";

	private static final String CHOOSE_CUISINE_FOR_MAIN_COURSE = "Choose a cuisine for a main course:";
	
	private static final String CHOOSE_MAIN_COURSE = "Choose a main course:";

	private static final String CHOOSE_CUISINE_FOR_DESSERT = "Choose a cuisine for a dessert:";
	
	private static final String CHOOSE_DESSERT = "Choose a dessert:";

	private static final String CHOOSE_DRINK = "Choose a drink:";
	
	private static final String IF_ICE_CUBES = "Would you like ice cubes to your drink? (Y/N)";
	
	private static final String IF_LEMON = "Would you like lemon to your drink? (Y/N)";

	private static final String INCORRECT_INPUT = "Incorrect input!";

	private static final String INCORRECT_NUMBER = "Incorrect number!";
	
	
	private static List<Order> orders = new ArrayList<>();
	private static List<Dish> dishes = new ArrayList<>();
	private static List<Drink> drinks = new ArrayList<>();
	
	public static void main(String[] args) {
		
		initProducts();

		try(Scanner scanner = new Scanner(System.in)) {
			
			//Begin taking orders:
			while(true) {
				
				//Ask what to order:
				Integer choiceNumber = null;
				while(choiceNumber == null) {
					System.out.println(ORDER_LUNCH_OR_DRINK);
					choiceNumber = getUserNumericChoice(scanner, 2);
				}
				
				switch(choiceNumber) {
				case 1: orderLunchProcedure(scanner);
					break;
				case 2: orderDrinkProcedure(scanner);
					break;
				}
				
				System.out.println("\nThank you for your order!\n");
			}
		}
	}

	private static void orderLunchProcedure(Scanner scanner) {
		
		//Ask for the main course cuisine:
		Integer mainCourseCuisineChoiceNumber = null;
		while(mainCourseCuisineChoiceNumber == null) {
			System.out.println(CHOOSE_CUISINE_FOR_MAIN_COURSE);
			for(int i = 0; i < Cuisine.values().length; i++) {
				System.out.println("(" + (i+1) + "). " + Cuisine.values()[i]);
			}
			mainCourseCuisineChoiceNumber = getUserNumericChoice(scanner, Cuisine.values().length);
		}
		final Cuisine mainCourseCuisine = Cuisine.values()[mainCourseCuisineChoiceNumber-1];

		
		//Ask for the main course:
		
		ArrayList<Dish> mainCourses = new ArrayList<>(dishes);
		mainCourses.removeIf(d -> d.getCourseType() != CourseType.mainCourse || d.getCuisine() != mainCourseCuisine);
		
		Integer mainCourseChoiceNumber = null;
		while(mainCourseChoiceNumber == null) {
			System.out.println(CHOOSE_MAIN_COURSE);
			for(int i = 0; i < mainCourses.size(); i++) {
				System.out.println(String.format(PRODUCT_PRESENTATION, i+1, mainCourses.get(i).getName(), 
						formatPrice(mainCourses.get(i).getPrice())));
			}
			mainCourseChoiceNumber = getUserNumericChoice(scanner, mainCourses.size());
		}
		
		
		//Ask for the dessert cuisine:
		Integer dessertCuisineChoiceNumber = null;
		while(dessertCuisineChoiceNumber == null) {
			System.out.println(CHOOSE_CUISINE_FOR_DESSERT);
			for(int i = 0; i < Cuisine.values().length; i++) {
				System.out.println("(" + (i+1) + "). " + Cuisine.values()[i]);
			}
			dessertCuisineChoiceNumber = getUserNumericChoice(scanner, Cuisine.values().length);
		}
		final Cuisine dessertCuisine = Cuisine.values()[dessertCuisineChoiceNumber-1];

		
		//Ask for the dessert:
		
		ArrayList<Dish> desserts = new ArrayList<>(dishes);
		desserts.removeIf(d -> d.getCourseType() != CourseType.dessert || d.getCuisine() != dessertCuisine);
		
		Integer dessertChoiceNumber = null;
		while(dessertChoiceNumber == null) {
			
			System.out.println(CHOOSE_DESSERT);
			for(int i = 0; i < desserts.size(); i++) {
				System.out.println(String.format(PRODUCT_PRESENTATION, i+1, desserts.get(i).getName(), formatPrice(desserts.get(i).getPrice())));
			}
			dessertChoiceNumber = getUserNumericChoice(scanner, desserts.size());
		}
		
		//Add new order:
		orders.add(new LunchOrder(mainCourses.get(mainCourseChoiceNumber-1), desserts.get(dessertChoiceNumber-1)));
	}

	private static void orderDrinkProcedure(Scanner scanner) {
		
		//Ask for the drink:
		Integer choiceNumber = null;
		while(choiceNumber == null) {
			System.out.println(CHOOSE_DRINK);
			for(int i = 0; i < drinks.size(); i++) {
				System.out.println(String.format(PRODUCT_PRESENTATION, i+1, drinks.get(i).getName(), 
						formatPrice(drinks.get(i).getPrice())));
			}
			choiceNumber = getUserNumericChoice(scanner, drinks.size());
		}
		
		//Ask if ice cubes:
		Boolean ifIceCubes = null;
		while(ifIceCubes == null) {
			System.out.println(IF_ICE_CUBES);
			ifIceCubes = getUserYesNoChoice(scanner);
		}
		
		//Ask if lemon:
		Boolean ifLemon = null;
		while(ifLemon == null) {
			System.out.println(IF_LEMON);
			ifLemon = getUserYesNoChoice(scanner);
		}
		
		//Add new order:
		orders.add(new DrinkOrder(drinks.get(choiceNumber-1), ifIceCubes, ifLemon));
	}

	private static Integer getUserNumericChoice(Scanner scanner, int range) {
		Integer choiceNumber = null;
		String choice = scanner.nextLine();
		if(!choice.matches(NUMERIC_ANSWER_PATTERN)){
			System.out.println(INCORRECT_INPUT);
			return null;
		}
		choiceNumber = Integer.valueOf(choice.replaceAll(NON_DIGIT_CHAR_PATTERN,""));
		if(choiceNumber < 1 || choiceNumber > range) {
			System.out.println(INCORRECT_NUMBER);
			return null;
		}
		return choiceNumber;
	}
	
	private static Boolean getUserYesNoChoice(Scanner scanner) {
		String choice = scanner.nextLine();
		if(choice.matches(YES_PATTERN)) {
			return true;
		} else if(choice.matches(NO_PATTERN)) {
			return false; 
		} else {
			System.out.println(INCORRECT_INPUT);
			return null;
		}
	}


	private static String formatPrice(BigDecimal currencyAmount) {
	    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currencyLocale);
	    return currencyFormatter.format(currencyAmount);
	}
	
	private static void initProducts() {
		dishes.add(new Dish(Cuisine.Polish, "Pierogi Ruskie", CourseType.mainCourse, new BigDecimal("10.5")));
		dishes.add(new Dish(Cuisine.Polish, "Barszcz Ukraiñski", CourseType.mainCourse, new BigDecimal("8.5")));
		dishes.add(new Dish(Cuisine.Polish, "Faworki", CourseType.dessert, new BigDecimal("9.0")));
		dishes.add(new Dish(Cuisine.Polish, "Szarlotka", CourseType.dessert, new BigDecimal("7.0")));

		dishes.add(new Dish(Cuisine.Mexican, "Taco", CourseType.mainCourse, new BigDecimal("8.5")));
		dishes.add(new Dish(Cuisine.Mexican, "Burito", CourseType.mainCourse, new BigDecimal("10.0")));
		dishes.add(new Dish(Cuisine.Mexican, "Rice pudding", CourseType.dessert, new BigDecimal("6.25")));
		dishes.add(new Dish(Cuisine.Mexican, "Marzipan", CourseType.dessert, new BigDecimal("14.5")));

		dishes.add(new Dish(Cuisine.Italian, "Pizza", CourseType.mainCourse, new BigDecimal("15.0")));
		dishes.add(new Dish(Cuisine.Italian, "Lazania", CourseType.mainCourse, new BigDecimal("12.5")));
		dishes.add(new Dish(Cuisine.Italian, "Tiramisu", CourseType.dessert, new BigDecimal("11.0")));
		dishes.add(new Dish(Cuisine.Italian, "Zabaione", CourseType.dessert, new BigDecimal("9.0")));

		
		drinks.add(new Drink("Mineral water", new BigDecimal("3.0")));
		drinks.add(new Drink("Tea", new BigDecimal("4.0")));
		drinks.add(new Drink("Coffee", new BigDecimal("6.5")));
		drinks.add(new Drink("Milk", new BigDecimal("5.0")));
		drinks.add(new Drink("Cola", new BigDecimal("5.0")));
	}
}
