package engine;

import javafx.scene.shape.Path;

public class Cards {
	public int power;
	public String color;
	String figure;
    public String sygnature;
    public String pathToImage;
    public boolean wasUsed=false;
	public Cards(int power,String color,String figure,String sygnature,String path){
		this.power=power;
		this.figure=figure;
		this.color=color;
		this.sygnature=sygnature;
		this.pathToImage=path;
	}
	
	public void setUsed() {
		this.wasUsed=true;
	}
	
	public void setNotUsed() {
		this.wasUsed=false;
	}
	
	/*
	 
	final Cards ninePik = new Cards(0,"pik","nine","PN");//PN
	final Cards tenPik = new Cards(10,"pik","ten","PT");//PT
	final Cards asPik = new Cards(11,"pik","as","PA");//PA
	final Cards quenePik = new Cards(3,"pik","quene","PQ");//PQ
	final Cards kingPik = new Cards(4,"pik","king","PK");//PK
	final Cards jackPik = new Cards(2,"pik","jack","PJ");//PJ
	final Cards nineCaro = new Cards(0,"caro","nine","CN");//CN
	final Cards tenCaro = new Cards(10,"caro","ten","CT");//CT
	final Cards asCaro = new Cards(11,"caro","as","CA");//CA
	final Cards queneCaro = new Cards(3,"caro","quene","CQ");//CQ
	final Cards kingCaro = new Cards(4,"caro","king","CK");//CK
	final Cards jackCaro = new Cards(2,"caro","jack","CJ");//CJ
	final Cards nineTrefl = new Cards(0,"trefl","nine","TN");//TN
	final Cards tenTrefl = new Cards(10,"trefl","ten","TT");//TT
	final Cards asTrefl = new Cards(11,"trefl","as","TA");//TA
	final Cards queneTrefl = new Cards(3,"trefl","quene","TQ");//TQ
	final Cards kingTrefl = new Cards(4,"trefl","king","TK");//TK
	final Cards jackTrefl = new Cards(2,"trefl","jack","TJ");//TJ
    final Cards nineKier = new Cards(0,"kier","nine","KN");//KN
 	final Cards tenKier = new Cards(10,"kier","ten","KT");//KT
	final Cards asKier = new Cards(11,"kier","as","KA");//KA
	final Cards queneKier = new Cards(3,"kier","quene","KQ");//KQ
	final Cards kingKier = new Cards(4,"kier","king","KK");//KK
	final Cards jackKier = new Cards(2,"kier","jack","KJ");//KJ
	 
	 pik winno
	 caro dzwonek
	 trefl - ¿o³¹dz
	 kier czerwieñ
	*/
	/*
	 nine
	 ten
	 queene
	 king
	 as
	 jack
	 */
}
