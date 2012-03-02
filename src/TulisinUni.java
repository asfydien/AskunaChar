/* Menggambar Aksara Sunda Per Karakter
 * Copyright (C) 2011 A. Sofyan Wahyudin
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

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class TulisinUni {
    
    private Font font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    private Image imgFont=null;
    private int idxFont[], cl;
    
    public TulisinUni(){

        init("/font/sundauni.png");
    
    }
    
    /**
     * 
     * @param fontpng path
     */
    public TulisinUni(String fontpng){

        init(fontpng);
    
    }
    
    private void init(String s){
        Vector vt = new Vector();
        
        imgFont = getImage(s);
        int rgb[] = new int[imgFont.getWidth()*imgFont.getHeight()];
        
        imgFont.getRGB(rgb, 0, imgFont.getWidth(), 0, 0, imgFont.getWidth(), imgFont.getHeight());
        
        // index char
        for (int i=0; i<rgb.length; i++){
            if (rgb[i]==0xff0000ff)
                vt.addElement(Integer.toString(i+1));
            else if (rgb[i]==0xffff0000){
                vt.addElement(Integer.toString(i+1));
                break;
            }
        }

        idxFont = new int[vt.size()];
        for(int i=0; i<vt.size(); i++)
            idxFont[i] = Integer.parseInt((String)vt.elementAt(i));
        
    }
    
    /**
     * 
     * @param cl color
     */
    public void setColor(int cl){
        this.cl = cl;
        
        int rgb[] = new int[imgFont.getWidth()*imgFont.getHeight()];  

        imgFont.getRGB(rgb, 0, imgFont.getWidth(), 0, 0, imgFont.getWidth(), imgFont.getHeight());  
        
        for (int i=idxFont[idxFont.length-1]; i<rgb.length; i++)
            if ((rgb[i] & 0x00ffffff) ==0x00000000) rgb[i] = (rgb[i] & 0xff000000) + (cl & 0x00ffffff);
        
        imgFont = Image.createRGBImage(rgb, imgFont.getWidth(), imgFont.getHeight(), true);
        
        rgb = null;
    }

    /**
     * perkiraan panjang
     * @param s teks unicode
     */
    public int stringWidth(String s){
        char ch[] = s.toCharArray();
        int  w=0;
        
        for (int i=0; i<ch.length; i++){
         
            // swara, ngalagena, angka
            if ((ch[i] >= 0x1b83 & ch[i] <= 0x1ba0) | ch[i] == 0x1bae |
                 ch[i] == 0x1baf | (ch[i] >= 0x1bb0 & ch[i] <= 0x1bb9))
                w += charWidth(ch[i])+2;
            // pangwisad, panélég, panolong
            else if (ch[i] == 0x1ba1 | ch[i] == 0x1ba6 | ch[i] == 0x1ba7)
                w += charWidth(ch[i])+1;
            // pamingkal
            else if (ch[i] == 0x1b82) 
                w += charWidth('\u1ba1'); // perkiraan sarua jeung pangwisad
        }
        
        return w;
    }
    
    public int charWidth(char c){
        
        
        if (c==0x20)    // spasi
            return 3;
        else if (c >= 0x1b80 | c <= 0x1ba9){ // char sunda
            int n = c - 0x1b80;
            return (idxFont[n+1]-idxFont[n])-1;
        }
        else
            return font.charWidth(c);
    }
    
    public int getHeight(){
        return imgFont.getHeight()-1;
    }
    
    /**
     * 
     * @param clBack background color
     * @return width of text
     */
    public int drawString(Graphics g, String s, int x, int y, int clBack){

        int lastxAksara=x, lastx=x, lastw=0, w;
        int hImg = imgFont.getHeight()-1;
        
        char ch[] = s.toCharArray();
        char c, cNext1, cNext2;
        
        g.setFont(font);
        g.setColor(cl);
        
        for (int i = 0; i<ch.length; i++){
            c = ch[i];
            
            cNext1 = (ch.length>i+1) ? ch[i+1] : 0;
            cNext2 = (ch.length>i+2) ? ch[i+2] : 0;
            
            System.out.println("CHR " + i + " : " +  Integer.toHexString(c));
            
            // char hurup | kha | sya
            // char angka | vertical line
            if ((c >= 0x1b83 & c <= 0x1ba0) | c == 0x1bae | c == 0x1baf |   
                (c >= 0x1bb0 & c <= 0x1bb9) | c == 0x7c) {                  
                
                // lamun char salanjutna panélég, pihelakeun ameh diharep
                if (cNext1 == 0x1ba6 | cNext2 == 0x1ba6){
                    w = drawChar(g, '\u1ba6', lastx, y+6, 0);
                    lastx += w + 1;
                } 

                w = drawChar(g, c, lastx, y, clBack);

                lastx += w + 2;
                lastxAksara = lastx;
                lastw = w;
                
            }
            // pamingkal | panyakra | panyiku
            else if (c == 0x1ba1 | c == 0x1ba2 | c == 0x1ba3) {
                
                // extra y lamun aya panyuku
                int xtraY = (cNext1 == 0x1ba5) ? 5 : 0; 
           
                
                if (c == 0x1ba1){
                    drawChar(g, c, (lastx-16), y+6+xtraY, 0);
                    lastx += 6; 
                }
                else if (c == 0x1ba2 | c == 0x1ba3){
                    w = charWidth(c);
                    drawChar(g, c, lastx-((lastw/2)+(w/2))-3, y+hImg+1+xtraY, 0);
                }
                
            }
            // panyecek | panglayar | panghulu | pamepet | paneuleug
            else if (c == 0x1b80 | c == 0x1b81 | c == 0x1ba4 | c == 0x1ba8 | c == 0x1ba9) { // rarangkén luhur
                
                w = charWidth(c);
                
                // double rarangkén
                // panyecek | panglayar | panghulu | pamepet | paneuleug
                if (cNext1 == 0x1b80 | cNext1 == 0x1b81 | cNext1 == 0x1ba4 | cNext1 == 0x1ba8 | cNext1 == 0x1ba9){
                    
                    int w2 = w + charWidth(cNext1);
                    int x2 = lastxAksara-((lastw/2)+(w2/2))-2;
                    
                    drawChar(g, c, x2, y-5, 0);
                    drawChar(g, cNext1, x2+w+1, y-5, 0);
                    
                    i+=1;   // luncat
                    
                } else
                    drawChar(g, c, lastxAksara-((lastw/2)+(w/2))-2, y-5, 0);
                
            }
            // pangwiasad | panolong | pamaeh)
            else if (c == 0x1b82 | c == 0x1ba7 | c == 0x1baa) {   // rarangkén di katuhu

                w = charWidth(c);

                drawChar(g, c, lastx-1, y+6, 0);
                
                lastx += w + 1;
                lastw = w;
                
            }
            // panyuku
            else if (c == 0x1ba5) {
                w = charWidth(c);
                drawChar(g, c, lastxAksara-((lastw/2)+(w/2))-3, y+hImg+1, 0);
            }
            // spasi
            else if (c == 0x20) {
                
                lastx += 3;
                System.out.println("---------------");
                
            }
            // salain char sunda tulis pake font biasa
            else if (c < 0x1b80 | c > 0x1bb9){
                g.drawString(String.valueOf(c), lastx + 2, y+hImg, g.LEFT | g.BASELINE);
                
                lastx += font.charWidth(c)+4;
                lastw = font.charWidth(c)+4;  
            }
            
        }
        
        return lastx-x;
    } 
    
    // Nyandak gambar
    private static Image getImage(String s){
        Image img = null;
        
        try{
            img = Image.createImage(s);
        } catch (Exception e) { }
        
        return img;
    }
    
    public int drawChar(Graphics g, int c, int x, int y, int clBack){
        
        int n, w;
        
        if (c == 0x7c)    // char pembatas angka
            n = idxFont.length-2;
        else
            n = c - 0x1b80; // mulai char sunda = 0x1b80
        
        w = idxFont[n+1]-idxFont[n]-1;
        
        if (clBack!=0){
            g.setColor(clBack);
            g.fillRect(x, y, w, getHeight());
        }
        
        g.drawRegion(imgFont, idxFont[n], 1, w, imgFont.getHeight()-1, Sprite.TRANS_NONE, x, y, g.LEFT | g.TOP);
        
        return w;
    }
}
