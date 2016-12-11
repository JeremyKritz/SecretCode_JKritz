package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;
// PUT IN ALL ERRORS AND EXCEPTIONS AND SHIT
/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		// COMPLETE THIS METHOD
		printList(deckRear);
		CardNode ptr = deckRear.next;
		if(deckRear.cardValue == 27){
			int temp = deckRear.cardValue;
			deckRear.cardValue = deckRear.next.cardValue;
			deckRear.next.cardValue = temp;
		}
		else{
		while (ptr.next.cardValue != 27){
			ptr = ptr.next;}
		
		CardNode j = ptr.next;
		if (j.next == deckRear){
			int temp = deckRear.cardValue;
			deckRear.cardValue = j.cardValue;
			j.cardValue = temp;
		}
		else{
		ptr.next = ptr.next.next;
		ptr = ptr.next;
		CardNode s = ptr.next;
		ptr.next = j;
		j.next = s;}}
		System.out.println("A");
		printList(deckRear);	
		
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
		CardNode ptr = deckRear;
		CardNode last = null;
		
		while (ptr.next.cardValue != 28){
			ptr = ptr.next;}
	
		if(ptr.next == deckRear){
			last = ptr.next.next;
		}
		else if(ptr.next.next == deckRear){
			last = ptr.next.next.next;
		}
		
		CardNode j = ptr.next;
		if (j.next.next == deckRear){
			last = j;
		}
		ptr.next = ptr.next.next;
		ptr = ptr.next.next;
		CardNode s = ptr.next;
		ptr.next = j;
		j.next = s;
		if (last != null){
			deckRear = last;
		}
		System.out.println("B");
		printList(deckRear);
		
		
	    // COMPLETE THIS METHOD
	}
	
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		// COMPLETE THIS METHOD
		// CANT HANDLE A JOKER AT THE FRONT
		
		CardNode stop1 = deckRear;
		while (stop1.next.cardValue != 27 && stop1.next.cardValue != 28){
			stop1 = stop1.next;}
		CardNode j1 = stop1.next;
		CardNode stop2 = j1;
		do {
			stop2 = stop2.next;}
		while (stop2.cardValue != 27 && stop2.cardValue != 28);
		
		CardNode j2 = stop2;
		stop2 = stop2.next;
		CardNode stop3 = deckRear;
		CardNode start1 = deckRear.next;
		//System.out.println(stop1.cardValue);
		//System.out.println(stop2.cardValue);
		
		
		if (deckRear.next == j1 && deckRear == j2){
		}
		else if(deckRear.next == j1){
			deckRear = j2;
		}
		else if(deckRear == j2){
			deckRear = stop1;
		}
		
		else{
		stop1.next = stop2;
		stop3.next = j1;
		j2.next = start1;
		deckRear = stop1;}
		System.out.println("Trip");
		printList(deckRear);
		//System.out.println(start1.cardValue);
		//System.out.println(stop3.cardValue);
		
		
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		// COMPLETE THIS METHOD
		int x = deckRear.cardValue;
		if (x == 28){
			x = 27;
		}
		int ct = 1;
		CardNode first = deckRear.next;
		CardNode ptr  = first;
		while(ct < x){
		ptr = ptr.next;
		ct++;}
		//System.out.println(ptr.cardValue);
		CardNode bRear = deckRear.next;
		while (bRear.next != deckRear){
			bRear = bRear.next;
		}
		//System.out.println(bRear.cardValue);
		bRear.next = first;
		deckRear.next = ptr.next;
		ptr.next = deckRear;
		System.out.println("Ct");
		printList(deckRear);
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		int key = 0;;
		do{
		jokerA();
		jokerB();
		tripleCut();
		countCut();
		int first = deckRear.next.cardValue;
		if (first == 28){
			first = 27;
		}
		CardNode ptr = deckRear.next;
		
		for (int i = 1; i< first; i++){
			ptr = ptr.next;
		}
		key = ptr.next.cardValue;
		}
		while (key == 27 || key == 28);
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return key;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		printList(deckRear);
		String msg = "";
		for (int i = 0; i < message.length(); i++){
			char x = message.charAt(i);
			if (Character.isLetter(x)){
				x = Character.toUpperCase(x);
				msg = msg + x;
			}
		}
		System.out.println(msg);
		String enc = "";
		int key;
		for (int j = 0; j<msg.length(); j++){
			key = getKey();
			System.out.println("Key: " + key);
			char x = msg.charAt(j);
			int toInt = x - 'A' + 1;
			//System.out.println(toInt);
			int fnlN = toInt + key;
			if (fnlN > 26){
				fnlN = fnlN - 26;}
			//System.out.println(fnlN);
			char fnCh = (char) (fnlN - 1 + 'A');
			
			enc = enc + fnCh;
		}
		
		
		
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return enc;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		printList(deckRear);
		String msg = "";
		for (int i = 0; i < message.length(); i++){
			char x = message.charAt(i);
			if (Character.isLetter(x)){
				x = Character.toUpperCase(x);
				msg = msg + x;
			}
		}
		System.out.println(msg);
		String denc = "";
		int key;
		for (int j = 0; j<msg.length(); j++){
			key = getKey();
			System.out.println("Key: " + key);
			char x = msg.charAt(j);
			System.out.println(x);
			int toInt = x - 'A' + 1;
			//System.out.println(toInt);
			int fnlN = toInt - key;
			if (fnlN < 0){
				fnlN = fnlN + 26;}
			//System.out.println(fnlN);
			char fnCh = (char) (fnlN - 1 + 'A');
			if(fnCh == '@'){
				fnCh = 'Z';
			}
			
			denc = denc + fnCh;
		}
		
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    return denc;
	}
}
