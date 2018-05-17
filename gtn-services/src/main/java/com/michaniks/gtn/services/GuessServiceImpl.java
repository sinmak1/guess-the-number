package com.michaniks.gtn.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.michaniks.gtn.dao.GameDAO;
import com.michaniks.gtn.entities.Guess;

@Stateless
public class GuessServiceImpl implements GuessService {
	
	@EJB
	private GameDAO gameDao;
	
	@Override
	public Guess checkGuess(Guess guess) {
		checkNumber(guess);
		return guess;
	}
	
	private Guess checkNumber(Guess guess) {
		Integer[] guessedNumberArray = convertToArray(guess.getGuessedNumber());
		Integer[] numberToGuess = gameDao
				.getGame(guess.getGameId())
				.getNumberToGuess();
		for (int i = 0; i < numberToGuess.length; i++) {
			for (int j = 0; j < guessedNumberArray.length; j++) {
				if (guessedNumberArray[j] == numberToGuess[i] && i == j) {
					guess.incrementCorrectPositions();
					guess.incrementCorrectNumbers();					
				} else if (guessedNumberArray[j] == numberToGuess[i]) {
					guess.incrementCorrectNumbers();					
				}
			}
		}
		return guess;
	}
	
	private Integer[] convertToArray(int number) {
		Integer[] numberArray = new Integer[4];
		for (int i = 3; i > -1; i--) {
			numberArray[i] = number % 10;
			number /= 10;
		}
		return numberArray;
	}
	
}