
import java.util.Vector;
import javax.microedition.lcdui.*;

public class KanvasConv extends Canvas implements CommandListener{
    Askuna midlet;
    
    private TextBox tbInput  = new TextBox("Masukan Teks", "", 160, 0);
    

    private Command cmdClose  = new Command("Close", Command.BACK, 1);
    private Command cmdConv   = new Command("Convert", Command.OK, 1);
    private Command cmdInput  = new Command("Input", Command.OK, 1);
    
    private ToSundaUni TSU = new ToSundaUni();
    private TulisinUni tiUni = new TulisinUni();
    
    private int xStart;
    
    public KanvasConv (Askuna midlet){
        this.midlet = midlet;
       
        addCommand(cmdInput);
        addCommand(cmdClose);
        
        tbInput.addCommand(cmdConv);
        tbInput.addCommand(cmdClose);
        tbInput.setCommandListener(this);
        
        setCommandListener(this);
        
        tiUni.setColor(0xffffff);
    }
    
    protected void keyPressed(int keyCode) {

    }
    
    public void commandAction(Command c, Displayable d) {
        
        if (c == cmdInput) Display.getDisplay(midlet).setCurrent(tbInput);
        
        if (c == cmdConv) {
            Display.getDisplay(midlet).setCurrent(this);
            repaint();
        }
        
        if (c == cmdClose){
            Golodog gg = new Golodog(midlet);
            Display.getDisplay(midlet).setCurrent(gg.lsMenu);
        }
    }
    
    
    protected void paint(Graphics g) {
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //g.setColor(0x000000);

        String part[] = split(tbInput.getString(), " ");
        
        int xStart = 5, yStart= 10;
        
        for (int i=0; i<part.length; i++){
            String sunda = TSU.convert(part[i]);
            
            if (xStart + tiUni.stringWidth(sunda) < getWidth())
                xStart += tiUni.drawString(g, sunda, xStart, yStart, 0) + 4;
            else{
                xStart = 5; yStart+=24;
                xStart += tiUni.drawString(g, sunda, xStart, yStart, 0);
            }
            
        }
    }

    private static String[] split(String s,String separator) {
        Vector nodes = new Vector();
        
        // potong2
        int index = s.indexOf(separator);
        while(index >= 0) {
            nodes.addElement( s.substring(0, index) );
            s = s.substring(index+separator.length());
            index = s.indexOf(separator);
        }
        
        // sesa
        nodes.addElement(s);

        // jieun array
        String[] result = new String[ nodes.size() ];
        if( nodes.size() > 0 ) {
            for(int loop = 0; loop < nodes.size(); loop++)
                result[loop] = (String)nodes.elementAt(loop);
        }
        
        return result;
    }

    
}
