/* Aksara Sunda J2ME
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

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;


public class Askuna extends MIDlet {
    public Display layar;
            
    public Askuna(){
        layar = Display.getDisplay(this);
    }
    
    public void startApp() {
        
        Golodog golodog = new Golodog(this);
        layar.setCurrent(golodog.lsMenu);
        
    }
    
    public void pauseApp() {
        
    }
    
    public void destroyApp(boolean unconditional) {
    
    }
    
    public void exitMIDlet(){
        destroyApp(true);
        notifyDestroyed();
    }
}
