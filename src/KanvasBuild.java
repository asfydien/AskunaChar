
import javax.microedition.lcdui.*;

public class KanvasBuild extends Canvas implements CommandListener{
    Askuna midlet;
    
    private Command cmdSelect  = new Command("Select", Command.OK, 1);
    private Command cmdApply  = new Command("Apply", Command.SCREEN, 2);
    private Command cmdClose = new Command("Close", Command.BACK, 2);
    
    private ToLatin TL = new ToLatin();
    private TulisinUni tiUni = new TulisinUni();
    private CharSel charSel = new CharSel(getWidth());
    
    int xStart;
    
    private Font fnLatin = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    
    private StringBuffer sbUntuy = new StringBuffer("");
    
    public KanvasBuild (Askuna midlet){
        this.midlet = midlet;
       
        addCommand(cmdSelect);
        addCommand(cmdClose);
        setCommandListener(this);

    }
    
    protected void keyPressed(int keyCode) {
        // hapus
        if (keyCode == Canvas.KEY_POUND || keyCode == -8)
            if (sbUntuy.length()>0) sbUntuy.delete(sbUntuy.length()-1, sbUntuy.length());
            
        // spasi
        if (keyCode == Canvas.KEY_NUM0)
            sbUntuy.append('\u0020');
            
        charSel.keyPressed(keyCode);
        
        repaint();
    }
    
    public void commandAction(Command c, Displayable d) {
        
        if (c == cmdApply) {
            sbUntuy.insert(sbUntuy.length(), charSel.getString());
            charSel.reset();
        }
        
        if (c == cmdSelect) charSel.select();
        
        if (c == cmdClose) {
            Golodog gg = new Golodog(midlet);
            Display.getDisplay(midlet).setCurrent(gg.lsMenu);
        }
        
        if (charSel.getString().equals("")) {
            removeCommand(cmdApply);
            addCommand(cmdClose);
        } else {
            removeCommand(cmdClose);
            addCommand(cmdApply);
        }
        
        repaint();
    }
    
    
    protected void paint(Graphics g) {
        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        charSel.render(g, getHeight(), getWidth());
        
        xStart = tiUni.drawString(g,  sbUntuy.toString(), 5, 10, 0);
        tiUni.drawString(g,  charSel.getString(), 5+xStart, 10, 0x97ABBF);
        
        g.setColor(0x718191);
        g.fillRect(0, (getHeight()/3)-2, getWidth(), 1);
        
        g.setFont(fnLatin);
        g.setColor(0x3B444C);
        g.drawString(TL.convert(sbUntuy.toString()), 5, getHeight()/3, g.LEFT | g.TOP);
        
        g.setColor(0x97ABBF);
        g.fillRect(4 + fnLatin.stringWidth(TL.convert(sbUntuy.toString())), getHeight()/3, fnLatin.stringWidth(TL.convert(charSel.getString()))+1, fnLatin.getHeight());
        
        g.setColor(0xffffff);
        g.drawString(TL.convert(charSel.getString()), 5 + fnLatin.stringWidth(TL.convert(sbUntuy.toString())), getHeight()/3, g.LEFT | g.TOP);
        
        
    }

}
