/* Porting untuk J2ME dari Macro Aksara Sunda untuk MS Office (Dian Tresna Nugraha)
 * Copyright (C) 2012 A. Sofyan Wahyudin
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

public class ToLatin {
    
    private String _latinChar;  // ref
    
    // angka
    private boolean matchNumber(char currChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1bb0':  _latinChar = "0";    break;
            case '\u1bb1':  _latinChar = "1";    break;
            case '\u1bb2':  _latinChar = "2";    break;
            case '\u1bb3':  _latinChar = "3";    break;
            case '\u1bb4':  _latinChar = "4";    break;
            case '\u1bb5':  _latinChar = "5";    break;
            case '\u1bb6':  _latinChar = "6";    break;
            case '\u1bb7':  _latinChar = "7";    break;
            case '\u1bb8':  _latinChar = "8";    break;
            case '\u1bb9':  _latinChar = "9";    break;
            default:
                    ret = false;
                    break;
        }

        return ret;
    }

    // konsonan sisip
    private boolean matchSubCons(char currChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1ba1':  _latinChar = "y";    break;
            case '\u1ba2':  _latinChar = "r";    break;
            case '\u1ba3':  _latinChar = "l";    break;
            default:
                ret = false;
                break;
        }

        return ret;
    }

    // panungtung
    private boolean matchEnding(char currChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1b80':  _latinChar = "ng";   break;
            case '\u1b81':  _latinChar = "r";    break;
            case '\u1b82':  _latinChar = "h";    break;
            default:
                    ret = false;
                    break;
        }

        return ret;
    }

    // sora vokal
    private boolean matchVocalSign(char currChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1ba4':  _latinChar = "i";    break;
            case '\u1ba5':  _latinChar = "u";    break;
            case '\u1ba6':  _latinChar = "é";    break;
            case '\u1ba7':  _latinChar = "o";    break;
            case '\u1ba8':  _latinChar = "e";    break;
            case '\u1ba9':  _latinChar = "eu";   break;
            default:
                    ret = false;
                    break;
        }

        return ret;
    }

    // vokal mandiri
    private boolean matchVocal(char currChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1b83':  _latinChar = "a";    break;
            case '\u1b84':  _latinChar = "i";    break;
            case '\u1b85':  _latinChar = "u";    break;
            case '\u1b86':  _latinChar = "é";    break;
            case '\u1b87':  _latinChar = "o";    break;
            case '\u1b88':  _latinChar = "e";    break;
            case '\u1b89':  _latinChar = "eu";   break;
            default:
                    _latinChar = ""; ret = false;
                    break;
        }

        return ret;
    }

    // konsonan ngalagena
    private boolean matchConsonant(char currChar){
        boolean ret = true;

        switch (currChar) {
            case '\u1b8a':  _latinChar = "k";    break;
            case '\u1b8b':  _latinChar = "q";    break;
            case '\u1b8c':  _latinChar = "g";    break;
            case '\u1b8d':  _latinChar = "ng";   break;
            case '\u1b8e':  _latinChar = "c";    break;
            case '\u1b8f':  _latinChar = "j";    break;
            case '\u1b90':  _latinChar = "z";    break;
            case '\u1b91':  _latinChar = "ny";   break;
            case '\u1b92':  _latinChar = "t";    break;
            case '\u1b93':  _latinChar = "d";    break;
            case '\u1b94':  _latinChar = "n";    break;
            case '\u1b95':  _latinChar = "p";    break;
            case '\u1b96':  _latinChar = "f";    break;
            case '\u1b97':  _latinChar = "v";    break;
            case '\u1b98':  _latinChar = "b";    break;
            case '\u1b99':  _latinChar = "m";    break;
            case '\u1b9a':  _latinChar = "y";    break;
            case '\u1b9b':  _latinChar = "r";    break;
            case '\u1b9c':  _latinChar = "l";    break;
            case '\u1b9d':  _latinChar = "w";    break;
            case '\u1b9e':  _latinChar = "s";    break;
            case '\u1b9f':  _latinChar = "x";    break;
            case '\u1ba0':  _latinChar = "h";    break;
            case '\u1bae':  _latinChar = "kh";    break; // <== tambah kha
            case '\u1baf':  _latinChar = "sy";    break; // <== tambah sya
            default:
                    _latinChar = ""; ret = false;
                    break;
        }

        return ret;
    }

    /**
     * 
     * @param iInputStr teks unicode Aksara Sunda
     * @return hasil konversi dari unicode
     */
    public String convert(String iInputStr){
        int    inputLen;
        String latinStr = "";
        char ch1, ch2, ch3, ch4;
        
        iInputStr = iInputStr.toLowerCase();
        inputLen  = iInputStr.length();

        while (inputLen > 0) {
            int matches = 1, numbers;
            boolean on;
            String latinCh1 = "";
            String latinCh2 = "";
            String latinCh3 = "";
            String latinCh4 = "";

            ch1 = charKa(iInputStr, 1);

            // syllable begins with consonant
            if (matchConsonant(ch1)) {  // V
                latinCh1 = _latinChar;

                ch2 = charKa(iInputStr, 2);

                if (ch2 == '\u1baa') { // pameh
                    latinCh2 = "";
                    matches  = 2;

                } else if (matchSubCons(ch2)) {
                    latinCh2 = _latinChar;

                    ch3 = charKa(iInputStr, 3);
                    
                    if (matchVocalSign(ch3)) {
                        latinCh3 = _latinChar;

                        ch4 = charKa(iInputStr, 4);

                        if (matchEnding(ch4) ) {
                            latinCh4 = _latinChar;
                            matches  = 4;
                        } else
                            matches  = 3;
                    
                    } else if (matchEnding(ch3)) {      // <= fixed tapi bingung ngalanggar silaba teu nya?
                        latinCh3 = "a" + _latinChar;    // <= ch: kyar, krar, klar ...
                        matches = 3;

                    } else {
                        matches  = 2;
                        latinCh3 = "a";
                    }

                } else if (matchVocalSign(ch2)) {
                    latinCh2 = _latinChar;

                    ch3 = charKa(iInputStr, 3);

                    if (matchEnding(ch3)) {
                        latinCh3 = _latinChar;
                        matches  = 3;
                    } else
                        matches = 2;

                } else {
                    latinCh2 = "a";

                    if (matchEnding(ch2)) {
                        latinCh3 = _latinChar;
                        matches  = 2;
                    }

                }

            // syllable begins with vowels
            } else if (matchVocal(ch1)) {
                latinCh1 = _latinChar;

                ch2 = charKa(iInputStr, 2);
                
                if (matchEnding(ch2)) {
                    latinCh2 = _latinChar;
                    matches = 2;
                }

            // number begins with pipe
            } else if (ch1 == '|') {
                latinCh1 = String.valueOf(ch1);

                on = true;
                numbers = 0;

                // try matching numbers
                do {

                    ch2 = charKa(iInputStr, 2+numbers);

                    if (matchNumber(ch2)) {
                        latinCh2  = _latinChar;
                        numbers  += 1;
                        latinCh1 += latinCh2;
                    } else
                        on = false;

                    latinCh2 = "";

                } while (on);

                matches += numbers;

                if (numbers > 0) {
                    latinCh1 = latinCh1.substring(1);

                    ch2 = charKa(iInputStr, 2+numbers);

                    if (ch2 == '|') matches += 1;
                }

            // bad formatted number, but ok we accept.
            } else if (matchNumber(ch1)) {

                latinCh1 = _latinChar;

                on = true;
                numbers = 0;

                // try matching other numbers
                do {
                    ch2 = charKa(iInputStr, 2+numbers);

                    if (matchNumber(ch2)) {
                        latinCh2 = _latinChar;
                        numbers  += 1;
                        latinCh1 += latinCh2;
                    } else
                        on = false;

                    latinCh2 = "";

                } while (on);

                matches += numbers;

            // others
            } else {
                latinCh1 = String.valueOf(ch1);
            }

            inputLen -= matches;
            latinStr += latinCh1 + latinCh2 + latinCh3 + latinCh4;

            if (inputLen > 0)
                iInputStr = iInputStr.substring(iInputStr.length()- inputLen);


        }   // while

        return latinStr;
    }
    
    private char charKa(String s, int i){
        return (s.length() > i-1) ? s.charAt(i-1) : ' ';
    }
    
}
