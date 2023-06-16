package de.mfroese.logic;

import de.mfroese.model.Password;
import de.mfroese.settings.AppTexts;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse speichert die Daten dieses Programmes in eine eigene<br>
 * <b>C</b>har / Commma-<b>S</b>eparated <b>V</b>alues - Datei.
 * Diese befindet sich unter resources/passwords.csv.<br>
 * <p>
 * Diese Klasse stellt
 * einen Threadsicheren und
 * Dateizugriff zur verfuegung.
 * <p>
 */
public class CsvFileHandler {
	
	//region 0. Konstanten
	private static final String CSV_FILE_PATH = "resource/passwords.csv";
	//endregion
	
	//region 1. Decl and Init Attribute
	/**
	 * Einzige Instanz die jemals existieren wird.
	 * Wird in der getOnlyInstanceOfThisClassEver()
	 * Einmal generiert. Alle anderen Klassen koennen
	 * druch den privaten Konstruktor {@link CsvFileHandler#CsvFileHandler()}
	 * keine Objekte mit anlegen:
	 * CsvFileHandler csvFileHandler = new CsvFileHandler() -> Geht einzig in allein
	 * nur in der Klasse CsvFileHandler
	 */
	private static CsvFileHandler onlyInstanceOfThisClassEver;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Privater Konstruktor
	 * Nur diese Klasse selbst
	 * soll bei dem Aufruf der
	 * Funktion getOnlyInstanceOfThisClassEver();
	 * ein einziges mal das Attribute Csv
	 */
	private CsvFileHandler() {
		System.out.println(CsvFileHandler.class.getSimpleName() + " generiert");
	}
	
	//endregion
	
	//region 3. GetOnlyInstanceOfThisClassEver
	
	/**
	 * Instanziiert beim ersten Aufruf(egal wo und wann dieser geschieht)
	 * die einzige Instanz die es zur gesamten Laufzeit des Programmes
	 * geben wird und liefert diese zurueck. So dass die Funktionen und Methoden
	 * dieser Klasse synchronisiert Thread und Zugriffssicher genutzt werden koennen
	 *
	 * @return :instance: {@link CsvFileHandler} : Einzige Instanz die jemals zur Laufzeit exisitieren wird.
	 */
	public static synchronized CsvFileHandler getOnlyInstanceOfThisClassEver() {
		
		//Checken ob das Objekt schonmal generiert wurde. Ist das so ist es nicht null
		if (onlyInstanceOfThisClassEver == null) {
			
			//Generiert diese Instanz ein einziges mal
			onlyInstanceOfThisClassEver = new CsvFileHandler();
		}
		
		return onlyInstanceOfThisClassEver;
	}
	//endregion
	
	//region 4. Speichern
	
	/**
	 * Speichert die mitgelieferte {@link List} von {@link Password}s
	 * in eine CSV-Datei
	 *
	 * @param passwordListToSave : {@link List} {@link Password}s : die gespeichert werden sollen
	 */
	public void saveToCsvFile(List<Password> passwordListToSave) {
		
		//0. Anlegen eins Dateiobjektes
		File csvFile = new File(CSV_FILE_PATH);
		
		//1. Informationen :/ und Weg zur Datei kann nur in Bytes schreiben
		FileOutputStream fos = null;
		
		//2. Schreibt in Byte :/ und setzt den Zeichensatz
		OutputStreamWriter osw = null;
		
		//3. Schreibt Strings :)
		BufferedWriter out = null;
		
		try {
			//Checken ob es Diese datei bereits gibt
			if (!csvFile.exists()) {
				
				//Anlegen sollte keine Datei existieren
				csvFile.createNewFile();
			}
			
			//1. Fos generieren mit Dateiobjekt generieren
			fos = new FileOutputStream(csvFile);
			
			//2. osw Zeichensatz setz mit dem fos
			osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			
			//3. out mit osw generieren
			out = new BufferedWriter(osw);
			
			//4. Gesamte Liste durchwandern
			for (Password n : passwordListToSave) {
				//Codieren des Inhalts
				String encryptedData = encrypt(n.getAllAttributesAsCsvString());

				//In Zwischenspeicher von BufferedWriter reinschreiben
				out.write(encryptedData + "\n");
			}
			
			
		} catch (Exception e) {
			System.err.println(CSV_FILE_PATH);
			e.printStackTrace();
		} finally {
			
			if (out != null) {
				try {
					//5. Immer Inhalt in die Datei schreiben egal ob eine Exception auftritt oder nich
					out.flush();
					
					//6. Verbindung zur Datei schließen
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	//endregion
	
	//region 5. Lesen
	
	/**
	 * Liest sollte die Datei {@link CsvFileHandler#CSV_FILE_PATH}
	 * existieren den kompletten Inhalt aus und gibt eine {@link List}e von
	 * passenden Models zureuck
	 *
	 * @return passwordListFromFile : {@link List} - {@link Password} : Notizen aus der Datei
	 */
	public List<Password> readFromCsvFile() {
		//0. Liste von Notizen
		List<Password> passwordListFromFile = new ArrayList<>();
		
		//1. Anlegen eins Dateiobjektes
		File csvFile = new File(CSV_FILE_PATH);
		
		//1. Informationen :/ und Weg zur Datei kann nur in Bytes lesen
		FileInputStream fis = null;
		
		//2. Liest in Byte :/ und setzt den Zeichensatz
		InputStreamReader isr = null;
		
		//3. Liest Strings :)
		BufferedReader in = null;
		
		try {
			//Checken ob es Diese datei bereits gibt
			if (csvFile.exists()) {
				
				//1. Fis generieren mit Dateiobjekt generieren
				fis = new FileInputStream(csvFile);
				
				//2. isr Zeichensatz setzen mit dem fis
				isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				
				//3. in mit isr generieren
				in = new BufferedReader(isr);
				
				//End Of File
				boolean eof = false;

				String encryptedLine;
				while ((encryptedLine = in.readLine()) != null) {
					String decryptedLine = decrypt(encryptedLine);
					Password password = new Password();
					password.setAllAttributesFromCsvLine(decryptedLine);
					passwordListFromFile.add(password);
				}
				
				//4. Sonalge das Ender der Datei nicht erreicht ist
				/*while (!eof) {
					
					//5. Aktuelle Zeile der Datei auslesen
					String readCsvLine = in.readLine();
					
					//6. Checken ob das Ende der Datei erreicht ist
					if (readCsvLine == null) {
						eof = true;
					} else {
						//Ende der Datei noch nicht erreicht
						
						//Neues Objekt anlegen
						Password currentPassword = new Password();
						
						//Mit Daten befuellen
						currentPassword.setAllAttributesFromCsvLine(readCsvLine);
						
						//Objekt in die Liste machen
						passwordListFromFile.add(currentPassword);
					}
					
				}*/
			}
			
		} catch (Exception e) {
			System.err.println(CSV_FILE_PATH);
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					//5. Immer Inhalt in die Datei schreiben egal ob eine Exception auftritt oder nich
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return passwordListFromFile;
	}
	//endregion

	//region 6. Verschluesselung
	private String encrypt(String text) {
		StringBuilder encryptedText = new StringBuilder();
		for (char c : text.toCharArray()) {
			int shiftedValue = (int) c;
			shiftedValue = 159 - shiftedValue;
			if (shiftedValue < 33) {
				shiftedValue = 126 - (33 - shiftedValue) + 1;
			}
			encryptedText.append((char) shiftedValue);
		}
		return encryptedText.toString();
	}

	private String decrypt(String encryptedText) {
		StringBuilder decryptedText = new StringBuilder();
		for (char c : encryptedText.toCharArray()) {
			int shiftedValue = (int) c;
			shiftedValue = 159 - shiftedValue;
			if (shiftedValue > 126) {
				shiftedValue = 33 + (159 - shiftedValue) + 1;
			}
			decryptedText.append((char) shiftedValue);
		}
		return decryptedText.toString();
	}
	//endregion
}