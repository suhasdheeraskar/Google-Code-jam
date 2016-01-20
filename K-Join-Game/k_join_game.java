

/*
 * In the exciting game of Join-K, red and blue pieces are dropped into an N-by-N table. The table stands up vertically so that pieces drop down to the bottom-most empty slots in their column. For example, consider the following two configurations:

    - Legal Position -

          .......
          .......
          .......
          ....R..
          ...RB..
          ..BRB..
          .RBBR..
   - Illegal Position -

          .......
          .......
          .......
          .......
   Bad -> ..BR...
          ...R...
          .RBBR..
In these pictures, each '.' represents an empty slot, each 'R' represents a slot filled with a red piece, and each 'B' represents a slot filled with a blue piece. The left configuration is legal, but the right one is not. This is because one of the pieces in the third column (marked with the arrow) has not fallen down to the empty slot below it.

A player wins if they can place at least K pieces of their color in a row, either horizontally, vertically, or diagonally. The four possible orientations are shown below:

      - Four in a row -

     R   RRRR    R   R
     R          R     R
     R         R       R
     R        R         R
In the "Legal Position" diagram at the beginning of the problem statement, both players had lined up two pieces in a row, but not three.
As it turns out, you are right now playing a very exciting game of Join-K, and you have a tricky plan to ensure victory! When your opponent is not looking, you are going to rotate the board 90 degrees clockwise onto its side. Gravity will then cause the pieces to fall down into a new position as shown below:

    - Start -

     .......
     .......
     .......
     ...R...
     ...RB..
     ..BRB..
     .RBBR..
     
   - Rotate -
     .......
     R......
     BB.....
     BRRR...
     RBB....
     .......
     .......
     
   - Gravity -
     .......
     .......
     .......
     R......
     BB.....
     BRR....
     RBBR...
Unfortunately, you only have time to rotate once before your opponent will notice.
All that remains is picking the right time to make your move. Given a board position, you should determine which player (or players!) will have K pieces in a row after you rotate the board clockwise and gravity takes effect in the new direction.

Notes

You can rotate the board only once.
Assume that gravity only takes effect after the board has been rotated completely.
Only check for winners after gravity has finished taking effect.
Input

The first line of the input gives the number of test cases, T. T test cases follow, each beginning with a line containing the integers N and K. The next N lines will each be exactly N characters long, showing the initial position of the board, using the same format as the diagrams above.

The initial position in each test case will be a legal position that can occur during a game of Join-K. In particular, neither player will have already formed K pieces in a row.

Output

For each test case, output one line containing "Case #x: y", where x is the case number (starting from 1), and y is one of "Red", "Blue", "Neither", or "Both". Here, y indicates which player or players will have K pieces in a row after you rotate the board.

Limits

1 ≤ T ≤ 100.
3 ≤ K ≤ N.

Small dataset

3 ≤ N ≤ 7.

Large dataset

3 ≤ N ≤ 50.

Sample


Input 
 	
Output 
 
4
7 3
.......
.......
.......
...R...
...BB..
..BRB..
.RRBR..
6 4
......
......
.R...R
.R..BB
.R.RBR
RB.BBB
4 4
R...
BR..
BR..
BR..
3 3
B..
RB.
RB.
Case #1: Neither
Case #2: Both
Case #3: Red
Case #4: Blue


 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class k_join_game {
	
	int N,k,T;
	char[][] matrix; 
	boolean r_won=false, b_won=false;
	
	void test(){
		N=6; k=4;
		//matrix = new char[N][N];
		char [][]mat = {
				{'.','.','.','.','.','.'},
				{'.','.','.','.','.','.'},
				{'.','R','R','R','.','R'},
				{'.','B','R','.','B','B'},
				{'.','R','.','B','R','B'},
				{'R','B','.','R','R','R'}
				};
		matrix = mat;
		
		rotate();
		find_pattern();
		if(r_won && b_won)
			System.out.println("Both");
		else if(r_won)
			System.out.println("Red");
		else if(b_won)
			System.out.println("Blue");
		else
			System.out.println("None");
	}
	
	void rotate(){
		for(int i=0;i<N;i++)
			for(int j=N-1;j>=0;j--){
				if(matrix[i][j]!='.')
					continue;
				else{// empty place
					int pos = j;
					int k=j;
					while(k>=0){
						if(matrix[i][k]!='.'){ // search till legitimate char found.
							char temp =matrix[i][pos];
							matrix[i][pos]=matrix[i][k];
							matrix[i][k]=temp;
							pos--;
						}
						k--;
					}
				}
			}
		
	}

	void find_pattern(){
		//Find horizontal
		for(int i=0;i<N;i++){
			boolean pattern=false;
			int r_count=0,b_count=0;
			for(int j=0;j<N;j++){
				if(matrix[i][j]=='R'){
					r_count++;b_count=0;
					if(r_count==k){
						r_won=true;
						//System.out.println("Horizontal-RED");
					}
				}else
					r_count=0;
				if(matrix[i][j]=='B'){
					b_count++;r_count=0;
					if(b_count==k){
						b_won=true;
						//System.out.println("Horizontal-BLUE");
					}
				}else
					b_count=0;
			}
		}
		
		//Find vertical
		for(int i=0;i<N;i++){
			boolean pattern=false;
			int r_count=0,b_count=0;
			for(int j=0;j<N;j++){
				if(matrix[j][i]=='R'){
					r_count++;b_count=0;
					if(r_count==k){
						r_won=true;
						//System.out.println("Vertical-RED");
					}
				}else
					r_count=0;
				if(matrix[j][i]=='B'){
					b_count++;r_count=0;
					if(b_count==k){
						b_won=true;
						//System.out.println("Vertical-BLUE");
					}
				}else
					b_count=0;
			}
			
			}
		
		//Find left-diagonal
		for(int row=0;row<N;row++){
			for(int col=0;col<N;col++){
				int r_count=0,b_count=0;
				if(matrix[row][col]=='R'){
					for(int i=row , j=col;j>=0 && i<N;){
						if(matrix[i][j]=='R'){
							r_count++;
							if(r_count==k){
								r_won=true;
								//System.out.println("Left diagonal-RED");
							}
						}else
							r_count=0;
						i++;j--;
					}
				}
				if(matrix[row][col]=='B'){
					for(int i=row , j=col;j>=0 && i<N;){
						if(matrix[i][j]=='B'){
							b_count++;
							if(b_count==k){
								b_won=true;
								//System.out.println("Left diagonal-BLUE");
							}
						}else
							b_count=0;
						i++;j--;
					}
				}
				
				
			}
		}
		
		//Find right-diagonal
		for(int row=0;row<N;row++){
			for(int col=0;col<N;col++){
				int r_count=0,b_count=0;
				if(matrix[row][col]=='R'){
					for(int i=row, j=col;i<N && j<N;){
						if(matrix[i][j]=='R'){
							r_count++;
							if(r_count==k){
								r_won=true;
								//System.out.println("Right diagonal-RED");
								}
						}else
							r_count=0;
						i++;j++;
						}
					}
				if(matrix[row][col]=='B'){
					for(int j=col, i=row;j<N && i<N;){
						if(matrix[i][j]=='B'){
							b_count++;
							if(b_count==k){
								b_won=true;
								//System.out.println("Right diagonal-BLUE");
							}
						}else
							b_count=0;
						i++;j++;
					}
				}
				
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		new k_join_game().start();
	}

	private void start() throws FileNotFoundException, UnsupportedEncodingException {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(new File("/home/ashutosh/Ninja/bin/A-large-practice.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(fs);
		T = Integer.parseInt(scanner.next());
		PrintWriter writer = new PrintWriter("large-output.txt", "UTF-8");
		for(int i=0;i<T;i++){
			
			N = Integer.parseInt(scanner.next());
			k = Integer.parseInt(scanner.next());
			matrix = new char[N][N];
			r_won=false;
			b_won=false;
			for(int j=0;j<N;j++){
				String input= scanner.next();
				for(int k=0;k<N;k++){
					matrix[j][k]=input.charAt(k);
				}
			}
			rotate();
			find_pattern();
			
			if(r_won && b_won)
				writer.println("Case #"+(i+1)+": Both");
			else if(r_won)
				writer.println("Case #"+(i+1)+": Red");
			else if(b_won)
				writer.println("Case #"+(i+1)+": Blue");
			else
				writer.println("Case #"+(i+1)+": Neither");
		}
		writer.close();
		
	}
		
}



