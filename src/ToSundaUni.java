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

public class ToSundaUni {
    private static final int T_NONE      = 0;
    private static final int T_VOCAL     = 1;
    private static final int T_CONSONANT = 2;
    private static final int T_NUMBER    = 3;

    
    private String _oOutChar, _oSundaToken;;   // ref
    private int _oMatches;  // ref

    // konsonan ngalagena
    private String getConsonant(String iCons)
    {
        if      (iCons.equals("k"))  return "\u1b8a";
        else if (iCons.equals("q"))  return "\u1b8b";
        else if (iCons.equals("g"))  return "\u1b8c";	
        else if (iCons.equals("c"))  return "\u1b8e";	
        else if (iCons.equals("j"))  return "\u1b8f";	
        else if (iCons.equals("z"))  return "\u1b90";	
        else if (iCons.equals("t"))  return "\u1b92";	
        else if (iCons.equals("d"))  return "\u1b93";	
        else if (iCons.equals("n"))  return "\u1b94";	
        else if (iCons.equals("p"))  return "\u1b95";	
        else if (iCons.equals("f"))  return "\u1b96";	
        else if (iCons.equals("v"))  return "\u1b97";	
        else if (iCons.equals("b"))  return "\u1b98";	
        else if (iCons.equals("m"))  return "\u1b99";	
        else if (iCons.equals("y"))  return "\u1b9a";	
        else if (iCons.equals("r"))  return "\u1b9b";	
        else if (iCons.equals("l"))  return "\u1b9c";	
        else if (iCons.equals("w"))  return "\u1b9d";	
        else if (iCons.equals("s"))  return "\u1b9e";	
        else if (iCons.equals("x"))  return "\u1b9f";	
        else if (iCons.equals("h"))  return "\u1ba0";	
        else if (iCons.equals("ng")) return "\u1b8d";	
        else if (iCons.equals("ny")) return "\u1b91";	
        else if (iCons.equals("kh")) return "\u1bae";	
        else if (iCons.equals("sy")) return "\u1baf";	
        else return iCons;
    }

    // konsonan sisip
    private String getConsonantR(String iCons)
    {
        if      (iCons.equals("y")) return "\u1ba1"; // pamingkal
        else if (iCons.equals("r")) return "\u1ba2"; // panyakra
        else if (iCons.equals("l")) return "\u1ba3"; // panyiku
        else    return iCons;
    }

    // panungtung
    private String getConsonantH(String iCons)
    {
        if      (iCons.equals("ng")) return "\u1b80";   // panyecek
        else if (iCons.equals("r"))  return "\u1b81";   // panglayar
        else if (iCons.equals("h"))  return "\u1b82";   // pangsiwad
        else    return getConsonant(iCons) + "\u1baa";  // pamaeh
    }

    // vokal swara
    private String getVocal(String iCons)
    {
        if      (iCons.equals("a"))  return "";
        else if (iCons.equals("i"))  return "\u1ba4";
        else if (iCons.equals("o"))  return "\u1ba7";
        else if (iCons.equals("u"))  return "\u1ba5";
        else if (iCons.equals("e"))  return "\u1ba8";
        else if (iCons.equals("eu")) return "\u1ba9";
        else if (iCons.equals("é"))  return "\u1ba6";
        else    return iCons;
    }

    // vokal mandiri
    private String getVocalM(String iCons)
    {
        if      (iCons.equals("a"))  return "\u1b83";
        else if (iCons.equals("i"))  return "\u1b84";
        else if (iCons.equals("o"))  return "\u1b87";
        else if (iCons.equals("u"))  return "\u1b85";
        else if (iCons.equals("e"))  return "\u1b88";
        else if (iCons.equals("eu")) return "\u1b89";
        else if (iCons.equals("é"))  return "\u1b86";
        else    return iCons.toUpperCase();
    }

    // angka
    private String getNumber(String iNum)
    {
        if      (iNum.equals("0"))  return "\u1bb0";
        else if (iNum.equals("1"))  return "\u1bb1";
        else if (iNum.equals("2"))  return "\u1bb2";
        else if (iNum.equals("3"))  return "\u1bb3";
        else if (iNum.equals("4"))  return "\u1bb4";
        else if (iNum.equals("5"))  return "\u1bb5";
        else if (iNum.equals("6"))  return "\u1bb6";
        else if (iNum.equals("7"))  return "\u1bb7";
        else if (iNum.equals("8"))  return "\u1bb8";
        else if (iNum.equals("9"))  return "\u1bb9";
        else    return iNum;
    }

    /**
     * Get first oMatches characters, copied into aOutChar from iInputStr, offseted by iOffset
     */
    private int getChar(String iInputStr, int iOffset, String oOutChar, int oMatches)
    {
        int ret = T_NONE, ch1, ch2, length;

        length = iInputStr.length();

        if (length > iOffset) {

            ch1 = (int)iInputStr.charAt(iOffset);

            if (length > iOffset + 1)
                ch2 = (int)iInputStr.charAt(iOffset + 1);
            else 
                ch2 = 0;


            oMatches = 1;
            if (ch1 == 0x65 & ch2 == 0x75)    // eu
            {
                ret = T_VOCAL;
                oMatches = 2;
            } 
            else if ((ch1 == 0x61) | (ch1 == 0x69) | (ch1 == 0x75) |
                     (ch1 == 0x65) | (ch1 == 0x6f) | (ch1 == 0xe9))   // a, i, u, e, o, é
                ret = T_VOCAL;
            else if ((ch1 == 0x6e & ch2 == 0x67) | (ch1 == 0x6e & ch2 == 0x79) |    // ng, ny
                     (ch1 == 0x6b & ch2 == 0x68) | (ch1 == 0x73 & ch2 == 0x79))     // <= tambah kh, sy)
            {
                ret = T_CONSONANT;
                oMatches = 2;
            }
            else if (ch1 >= 0x61 & ch1 <= 0x7a) // a .. z
                ret = T_CONSONANT;
            else if (ch1 >= 0x30 & ch1 <= 0x39)
                ret = T_NUMBER;
            else 
                oMatches = 1;

            oOutChar = iInputStr.substring(iOffset, iOffset + oMatches);

        } else
            oMatches = 0;


        _oOutChar = oOutChar;
        _oMatches = oMatches;

        return ret;
    }
    
    /**
     * @param iInputStr input string
     * @param oMatches number of matched characters in the token
     * @param oSundaToken token string extracted from iInputStr
     */
    private String nextLatinToken(String iInputStr, int oMatches, String oSundaToken)
    {
        String ret = "";
        int charType = 0;
        String ch1 = "";
        String ch2 = "";
        String ch3 = "";
        //string ch4 = "";
        int matches1 = 0;
        int matches2 = 0;
        int matches3 = 0;

        oSundaToken = "";
        oMatches = 0;

        charType = getChar(iInputStr, oMatches, ch1, matches1);
        ch1 = _oOutChar;
        matches1 = _oMatches;

        if ((charType == T_CONSONANT)) {
            //(K)? ..
            ret += ch1;
            oMatches += matches1;
            oSundaToken += getConsonant(ch1);

            if (!ch1.equals("ny") & !ch1.equals("ng") & !ch1.equals("n") & !ch1.equals("r")) {    // <= h dihapus supaya bisa "hyu"

                charType = getChar(iInputStr, oMatches, ch1, matches1);
                ch1 = _oOutChar;
                matches1 = _oMatches;

                if (ch1.equals("y") | ch1.equals("r") | ch1.equals("l")) {    // <= tambah l
                    //(KR)? ..
                    ret += ch1;
                    oMatches += matches1;
                    oSundaToken += getConsonantR(ch1);
                } else if ((charType == T_CONSONANT)) {
                    oSundaToken += "\u1baa";   // pamaeh
                }
            }

        }

        charType = getChar(iInputStr, oMatches, ch1, matches1);
        ch1 = _oOutChar;
        matches1 = _oMatches;

        if (charType == T_VOCAL) {

            // V ..
            if (oMatches == 0)
                oSundaToken += getVocalM(ch1);
            else
                oSundaToken += getVocal(ch1);

            ret += ch1;
            oMatches += matches1;

            charType = getChar(iInputStr, oMatches, ch2, matches2);
            ch2 = _oOutChar;
            matches2 = _oMatches;

            if (charType == T_CONSONANT) {

                // VK ..
                charType = getChar(iInputStr, oMatches + matches2, ch3, matches3);
                ch3 = _oOutChar;
                matches3 = _oMatches;

                if (charType == T_VOCAL) {
                    // VKV --> get only V
                } else if ((!ch2.equals("ny") & !ch2.equals("ng") & !ch2.equals("n") & !ch2.equals("h") & !ch2.equals("r")) &
                            (ch3.equals("r") | ch3.equals("y"))) {
                    // VKR --> get only V
                } else {
                    // VKX --> get the VK
                    ret += ch2;
                    oSundaToken += getConsonantH(ch2);
                    oMatches +=  matches2;
                }

            } else {
                    // VX --> get only V
            }
        } else if (charType == T_NUMBER) {

            do {
                // get all numbers
                ret += ch1;
                oSundaToken += getNumber(ch1);
                oMatches += matches1;

                charType = getChar(iInputStr, oMatches, ch1, matches1);
                ch1 = _oOutChar;
                matches1 = _oMatches;

            } while (charType == T_NUMBER);

            // put pipes
            oSundaToken = "|" + oSundaToken + "|";

        } else if (oMatches == 0) {
            // other type
            ret = ch1;
            oSundaToken += ch1;
            oMatches = matches1;
        }

        _oMatches = oMatches;
        _oSundaToken = oSundaToken;

        return ret;
    }

    /**
     * @param iInputStr teks yang akan dikonversi
     * @return teks unicode Aksara Sunda
     */
    public String convert(String iInputStr)
    {
        int    inputLen = 0;
        String sundaStr = "";
        String latinToken = "";
        String sundaToken = "";

        iInputStr = iInputStr.toLowerCase();
        inputLen = iInputStr.length();

        while (inputLen > 0) {
            int matches = 0;

            latinToken = nextLatinToken(iInputStr, matches, sundaToken);
            matches = _oMatches;
            sundaToken = _oSundaToken;

            sundaStr += sundaToken;
            inputLen -= matches;

            if (inputLen > 0)
                iInputStr = iInputStr.substring(iInputStr.length() - inputLen);

        }

        return sundaStr;
    }
    
}
