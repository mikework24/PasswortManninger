package de.mfroese.model;

/**
 * Datenklasse fuer ein einzelnes Passwort
 */
public class Password {
	
	//region 0. Konstanten
	private static final String NO_TITLE_YET      = ">noTitleYet<";
	private static final String NO_PASSWORD_YET   = ">noPasswordYet<";
	private static final String SPLIT_CHAR        = ";";
	private static final int SPLIT_INDEX_TITLE    = 0;
	private static final int SPLIT_INDEX_PASSWORD = 1;
	//endregion
	
	//region 1. Decl and Init Attribute
	private String title;
	private String password;
	//endregion
	
	//region 2. Konstruktoren
	/**
	 * 1. Standardkonstruktor
	 * weist allen Attributen einen vordefinierten Standardwert zu.
	 */
	public Password() {
		this.title    = NO_TITLE_YET;
		this.password = NO_PASSWORD_YET;
	}
	
	/**
	 * 2. Ueberladner Konstruktor
	 * weist mitgegebe Parameter zu.
	 *
	 * @param title    : {@link String} : Titel des Passworts
	 * @param password : {@link String} : Passwort
	 */
	public Password(String title, String password) {
		this.title = title;
		this.password = password;
	}
	//endregion
	
	//region 3. Getter und Setter
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String strTitle) {
		this.title = strTitle;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * TODO 0 getAllAttributesAsCsvString
	 * Alle Attribute als ein String mit
	 * einem Semikolon als Trennzeichen<br>
	 * Index          : 0        ;     1
	 * Attributname   : title ;password<br>
	 * Attributunhalt : Amazon;MEIN_PW
	 *
	 * @return allAttributesAsCsvString : {@link String} : Alle Attribute mit einem Semikolon getrennt als String
	 */
	public String getAllAttributesAsCsvString() {
		return this.title + SPLIT_CHAR + this.password;
	}
	
	/**
	 * TODO 1 setAllAttributesFromCsvString
	 * Nimmt einen von {@link Password#getAllAttributesAsCsvString()} generierten
	 * CSV-String entgegen und splitt diesen nach dem verwendeten Trennzeichen
	 * {@link Password#SPLIT_CHAR} auf und extrahiert die einzlen Attributwerte und weist diese zu.
	 * <p>
	 * Index          : 0        ;     1
	 * Attributname   : title ; password<br>
	 * Attributunhalt : Amazon;MEIN_PW
	 *
	 * @param readCsvLine : {@link String} : Ausgelesener CSV-String aus späterem {@link de.mfroese.logic.CsvFileHandler}
	 */
	public void setAllAttributesFromCsvLine(String readCsvLine) {
		//Alle Attribute nach dem Semikolon splitten
		String[] allAttributes = readCsvLine.split(SPLIT_CHAR);
		
		//Attribute setzen
		this.title    = allAttributes[SPLIT_INDEX_TITLE];
		this.password = allAttributes[SPLIT_INDEX_PASSWORD];
	}
	//endregion
	
	//region 4. Methoden und Funktionen
	@Override
	public String toString() {
		return "Note{" +
				"title='" + title + '\'' +
				", password='" + password + '\'' +
				'}';
	}
	
	
}