// level 2 graph
// LC: 785 isBipartite
class Solution {

    public boolean isBipartite(int[][] graph) {
        int[] col = new int[graph.length];
        Arrays.fill(col, -1);
        for(int i = 0; i < graph.length; i++){
            if(col[i] == -1)
            if(!isBipartite(i, graph, col, 1)){
                return false;
            }
        }
        return true;
    }
    
    public boolean isBipartite(int s, int[][] graph, int[] col, int cols){
        
        col[s] = cols;
        
        for(int e : graph[s]){
            if(col[e] == -1){
                
                if(!isBipartite(e, graph, col, 1 - cols)) return false;
            }else{
                if(col[e] == col[s]){
                    return false;
                }
            }
        }
        
        return true;
    }
}

// LC: 994 Rotten oranges
class Solution {
    class Pair{
        int i;
        int j;
        int t;
        Pair(){
            
        }
        Pair(int i, int j, int t){
            this.i = i;
            this.j = j;
            this.t = t;
        }
    }
    public int orangesRotting(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0 , -1}};
        int time = 0;
        Queue<Pair> que = new ArrayDeque<>();
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(grid[i][j] == 2){
                    que.add(new Pair(i, j, 0));
                }
            }
        }
        while(que.size() > 0){
            Pair rem = que.poll();
            time = rem.t;
            for(int d = 0; d < dir.length; d++){

                int nr = rem.i + dir[d][0];
                int nc = rem.j + dir[d][1];
                if(nr < grid.length && nr >= 0 && nc < grid[0].length && nc >= 0 && grid[nr][nc] == 1){
                que.add(new Pair(nr, nc, rem.t + 1));
                    grid[nr][nc] = 0;
            }

            }
        }
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(grid[i][j] == 1){
                    return -1;
                }
            }
        }
        return time;
    }
}
