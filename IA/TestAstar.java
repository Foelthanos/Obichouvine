import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Classe fournissant un cas de test concret pour l'algo A*
 * 
 * On veut parcourir une matric de chaine de caractère, un espace représentant un chemin exploitable
 * un | représentant un chemin interdit.
 * On peut se déplacer a gauche, a droite, en haut, en bas, mais pas en diagonal
 */
public class TestAstar {

	public static void main(String[] args) {
		final String[][] matrix = new String[][] {
			{ " ","|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," " },
			{ " ","|"," "," "," "," "," "," "," "," "," "," "," ","|"," "," "," "," " },
			{ " ","|","|"," "," "," "," "," "," "," "," "," "," ","|"," "," "," "," " },
			{ " "," ","|"," "," "," "," "," "," "," "," "," "," ","|"," "," "," "," " },
			{ " "," ","|"," "," "," "," "," "," "," "," "," "," ","|"," "," "," "," " },
			{ " "," ","|"," "," "," "," "," "," "," "," "," "," "," ","|"," ","|"," " },
			{ " "," ","|","|","|","|","|","|","|"," "," "," "," "," ","|"," "," "," " },
			{ " "," "," "," "," "," "," "," ","|"," "," "," ","|"," ","|","|"," "," " },
			{ " "," "," "," "," "," "," "," ","|"," "," "," ","|"," "," "," "," "," " },
			{ " "," "," "," "," "," ","|"," ","|"," "," "," ","|"," "," "," "," "," " },
			{ " ","|","|","|","|","|","|"," ","|"," "," "," ","|","|","|"," ","|","|" },
			{ " ","|"," "," "," "," "," "," ","|"," "," "," ","|"," "," "," "," "," " },
			{ " ","|"," "," "," "," "," "," ","|"," ","|"," ","|"," "," "," ","|"," " },
			{ " ","|"," "," "," "," "," "," ","|","|","|"," ","|"," "," "," "," "," " },
			{ " ","|"," "," "," "," "," "," "," "," "," "," ","|"," "," "," ","|"," " },
		};

		final int width = matrix[0].length - 1;
		final int height = matrix.length - 1;
		
		final SuccessorComputer<Point> successorComputer = new SuccessorComputer<Point>() {

			@Override
			public Collection<Point> computeSuccessor(final Node<Point> node) {
				final Point index = node.getIndex();
				final int x = (int) index.getX();
				final int y = (int) index.getY();

				final List<Point> resultat = new ArrayList<Point>();
				if (x > 0) {
					resultat.add(new Point(x - 1, y));
				}
				if (x < width ) {
					resultat.add(new Point(x + 1, y));
				}

				if (y > 0) {
					resultat.add(new Point(x, y - 1));
				}
				if (y < height ) {
					resultat.add(new Point(x, y + 1));
				}
				if(node.getParent() != null) {
					resultat.remove(node.getParent().getIndex());
				}
				return resultat;
			}
		};
		
		final NodeFactory<Point> nodeFactory = new NodeFactory<Point>() {
			@Override
			protected double computeReel(final Point parentIndex, final Point index) {
				if(parentIndex != null && parentIndex.equals(index)) {
					return 0;
				}
				
				if(" ".equals(matrix[(int) index.getY()][(int) index.getX()])) {
					return 1;
				}
				return Double.MAX_VALUE;
			}
			
			@Override
			protected double computeTheorique(final Point index, final Point goal) {
				return Math.abs(index.getX()-goal.getX()) + Math.abs(index.getY()-goal.getY());
			}
		};

		final AStar<Point> astart = new AStar<Point>(successorComputer,
				nodeFactory);
		
		final List<Point> result = astart.compute(new Point(0,0), new Point(width,height));
		
		//On intégre le résultat dans la matrice de base, et on l'affiche
		for(final Point point : result) {
			matrix[(int) point.getY()][(int) point.getX()] = "X";
		}
		displayMatrix(matrix);
	}

	public static void displayMatrix(final String[][] matrix) {
		final StringBuilder result = new StringBuilder();
		for (int col = 0; col < matrix[0].length; ++col) {
			result.append("___");
		}
		result.append('\n');
		for (int line = 0; line < matrix.length; ++line) {
			for (int col = 0; col < matrix[line].length; ++col) {
				result.append(' ');
				result.append(matrix[line][col]);
				result.append(' ');
			}

			result.append('\n');
		}
		for (int col = 0; col < matrix[0].length; ++col) {
			result.append("___");
		}
		System.out.println(result);
	}
}

