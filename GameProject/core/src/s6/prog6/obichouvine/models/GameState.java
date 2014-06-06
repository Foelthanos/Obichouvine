package s6.prog6.obichouvine.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import s6.prog6.obichouvine.models.Block.BlockState;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;

public class GameState {
   
    public GameState(){
       
       
    }
   
   
   
    public Board Sauver(Board b) throws IOException
    {
    	String sauv = null;
   
        Block[][] blocks = new Block[b.GetxBoard()][b.GetyBoard()];
        Board board = new Board(b.GetxBoard(),b.GetyBoard(),b.GetParameter());
       
        board.SetBlocks(b.getBlock());
       
        
        
        JFileChooser filechoose = new JFileChooser();
        filechoose.setCurrentDirectory(new  File(".")); 

        String approve = new String("ENREGISTRER");

        int resultatEnregistrer = filechoose.showDialog(filechoose,
     approve); 
        if (resultatEnregistrer ==   JFileChooser.APPROVE_OPTION)
 
        {  sauv= new String(filechoose.getSelectedFile().toString());
   
           if(sauv.endsWith(".sav")   || sauv.endsWith(".SAV")) {;}
    
           else sauv = sauv+ ".sav";

        }
       
        try {
        	File file = new File(sauv);  
        	FileWriter writer = new FileWriter(file, true); 
            PrintWriter fo = new PrintWriter(writer);
            
            System.out.println(b.GetxBoard());
            
            fo.println(b.GetxBoard() + " " + b.GetyBoard());
            fo.println(b.GetParameter().getEsc().toString());
            fo.println(b.GetParameter().getKingCap().toString());
            fo.println(b.GetParameter().getKingMove().toString());

           
           
            for (int i = 0; i < b.GetxBoard(); i++)
            {
                for (int j = 0; j < b.GetyBoard(); j++)
                {       
                    if (b.getBlock()[i][j].getPawn().getType() == Pawn.PawnType.SUEDOIS)
                    	fo.print(b.getBlock()[i][j].getPawn().getTypesuede());
                    else
                    	fo.print(b.getBlock()[i][j].getPawn());

                    fo.println(" " +b.getBlock()[i][j].getState());

                }
            }
               
                fo.close();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return board;
    }
   
   
    public Board Charger()
    {
    	Board board = null;
        int longueur = 0, largeur = 0;
        String sauv = null;
        
        int offsetX = (int) (Gdx.graphics.getWidth()/2 - (9*Block.SIZE)/2);
        int offsetY = (int) (Gdx.graphics.getHeight()/2 - (9*Block.SIZE)/2);
       
         JFileChooser chooser = new JFileChooser();
         chooser.setCurrentDirectory(new  File(".")); 

         FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sauvegardes", "sav");
            chooser.setFileFilter(filter);
         int returnVal = chooser.showOpenDialog(null);
         if(returnVal == JFileChooser.APPROVE_OPTION) {
              
                    sauv = chooser.getSelectedFile().getName();
            }
         Parameter parameter = new Parameter(null,null,null, null);
         Block[][] b = new Block[9][9];
         Pawn pawnn = new Pawn(null);

         if (sauv != null)
         {
            try {
                String sCurrentLine;
                int i =0;
                InputStream ips=new FileInputStream(sauv); 
    			InputStreamReader ipsr=new InputStreamReader(ips);
    			BufferedReader br =new BufferedReader(ipsr);
               
                while ((sCurrentLine = br.readLine()) != null || i <= 4) {
                    if (i==0)
                    {
                    	System.out.println("wesh");

                        String [] taille = sCurrentLine.split(" ");
                        longueur = Integer.parseInt(taille[0]);
                        largeur = Integer.parseInt(taille[1]);
                    }else if(i == 1)
                    {
                        String  param = sCurrentLine.toString();
                    	System.out.println(param);

                        if (param.contains("Coin"))
                        {
                            parameter.setEsc(Parameter.EscapeMethod.Corner);
                        }
                        else if (param.contains("Bord sauf rouge"))
                        {
                        	System.out.println("wesh");
                            parameter.setEsc(Parameter.EscapeMethod.EdgeWithoutMosco);
                        	System.out.println("wesh");

                        }
                        else if (param.contains("Bord"))
                        {
                            parameter.setEsc(Parameter.EscapeMethod.Edge);
                        }
                        else
                        {
                            break;
                        }
                    }else if(i == 2)
                    {
                        String  param = sCurrentLine.toString();
                        if (param.contains("Impossible"))
                        {
                        	System.out.println("wesh");
                            parameter.setKingCap(Parameter.KingCaptureMethod.Cannot);
                        }
                        else if (param.contains("Possible"))
                        {
                            parameter.setKingCap(Parameter.KingCaptureMethod.Can);
                        }
                        else if (param.contains("Pas un pilier"))
                        {
                            parameter.setKingCap(Parameter.KingCaptureMethod.NotAPillar);
                        }
                        else
                        {
                            break;
                        }
                   
                    }else if(i == 3)
                    {
                        String  param = sCurrentLine.toString();
                        if (param.contains("Illimité"))
                        {
                            parameter.setKingMove(Parameter.KingMoveMethod.Unlimited);
                        }
                        else if (param.contains("4 cases"))
                        {
                            parameter.setKingMove(Parameter.KingMoveMethod.FourBlock);
                        }
                        else if (param.contains("Obstrué"))
                        {
                            parameter.setKingMove(Parameter.KingMoveMethod.WithoutMosco);
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if (i==4)

                    {
                          
                        for (int j = 0; j < longueur; j++)
                        {
                            for (int k = 0; k < largeur; k++)
                            {       
                            	System.out.println(j);
                            	System.out.println(k);

                            	String[]  pawn = sCurrentLine.split(" ");
                            	if ((pawn[0]).equals("VIDE"))
                            			{
                            				pawnn = new Pawn(Pawn.PawnType.VIDE);
                                			//b[j][k].setPawn(pawnn);
                            			}
                            	else if ((pawn[0]).equals("PION"))
                    			{
                    				 pawnn = new Pawn(Pawn.PawnType.SUEDOIS,Pawn.TypeSuedois.PION);
                        			//b[j][k].setPawn(pawnn);
                    			}
                             	else if ((pawn[0]).equals("KING"))
                    			{
                    				 pawnn = new Pawn(Pawn.PawnType.SUEDOIS,Pawn.TypeSuedois.KING);
                        			//b[j][k].setPawn(pawnn);
                    			}
                            	else if ((pawn[0]).equals("MOSCOVITE"))
                    			{
                    				 pawnn = new Pawn(Pawn.PawnType.MOSCOVITE);
                        			//b[j][k].setPawn(pawnn);
                    			}
                            	
                            	if ((pawn[1]).toString().equals("BLANCEXIT"))
                            	{
                            		Block bloc = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), Block.BlockState.BLANCEXIT, pawnn);
                                	b[j][k] = bloc;
                            	}
                            	else if ((pawn[1]).toString().equals("ROUGEEXIT"))
                            	{
                            		Block bloc = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), Block.BlockState.ROUGEEXIT, pawnn);
                                	b[j][k] = bloc;
                            	}
                              	else if ((pawn[1]).toString().equals("BLANC"))
                            	{
                            		Block bloc = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), Block.BlockState.BLANC, pawnn);
                                	b[j][k] = bloc;
                            	}
                              	else if ((pawn[1]).toString().equals("ROUGE"))
                            	{
                            		Block bloc = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), Block.BlockState.ROUGE, pawnn);
                                	b[j][k] = bloc;
                            	}
                            	else if ((pawn[1]).toString().equals("TRONE"))
                            	{
                            		Block bloc = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), Block.BlockState.TRONE, pawnn);
                                	b[j][k] = bloc;
                            	}
                            	else if ((pawn[1]).toString().equals("FORTERESSE"))
                            	{
                            		Block bloc = new Block(new Vector2(offsetX+(i*Block.SIZE), offsetY+(j*Block.SIZE)), Block.BlockState.FORTERESSE, pawnn);
                                	b[j][k] = bloc;
                            	}
                            	else
                            	{
                            		System.out.println(i);
                            		break;
                            	}
                            	sCurrentLine = br.readLine();
            
                            }
                        }
                        board = new Board (9,9,parameter);

                        board.setXBoard(longueur);
                        board.setYBoard(largeur);
                        board.SetBlocks(b);
                        


                    }
                    i++;
                }
                        br.close();

            } catch (IOException e) {
                e.printStackTrace();
            } 
         }
		return board;
    }
   
}


