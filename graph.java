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


// LC : 815 Bus routes
class Solution {
    
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if(source == target){
            return 0;
        }
        HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
        
        for(int bus = 0; bus < routes.length; bus++){
            for(int stop : routes[bus]){
                if(!hm.containsKey(stop)){
                    hm.put(stop, new ArrayList<>());
                }
                hm.get(stop).add(bus);
            }
        }
        boolean[] vis = new boolean[routes.length];
        Queue<Integer> que = new ArrayDeque<>();
        for(int bus : hm.get(source)){
            que.add(bus);
        }
        int size = que.size();
        int busCount = 0;
        while(!que.isEmpty()){
            busCount += 1;
            int nsize = size;
            size = 0;
            for(int i = 0; i < nsize; i++){
                int cbus = que.poll();
                if(vis[cbus] == true){  // don't miss this line
                    continue;
                }
                vis[cbus] = true;
                
                for(int stop : routes[cbus]){
                    if(stop == target){
                        return busCount;
                    }
                }
                
                for(int stop : routes[cbus]){
                    for(int nbus : hm.get(stop)){
                        if(vis[nbus] == false){
                            que.add(nbus);
                            size++;
                        }
                    }
                }
                
            }
            
        }
        return -1;
    }
}

// LC: 694 Number of distinct islands
class Solution {
    public int numDistinctIslands(int[][] grid) {
        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        String[] sdir = {"D", "R", "U", "L"};
        StringBuilder ans = new StringBuilder();
        HashSet<String> structure = new HashSet<>();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1){
                    StringBuilder sb = getStructure(grid, i, j, sdir, dir);
                    structure.add(sb.toString());
                    System.out.println(sb.toString());
                }
            }
        }
        return structure.size();
    }
    public StringBuilder getStructure(int[][] grid, int i, int j, String[] sdir, int[][] dir){
        grid[i][j] = 0;
        StringBuilder ans = new StringBuilder();
        for(int k = 0; k < 4; k++){
            int nr = i + dir[k][0];
            int nc = j + dir[k][1];
            if(nr >= 0 && nc >= 0 && nr < grid.length && nc < grid[0].length && grid[nr][nc] == 1){             ans.append(sdir[k]);
                StringBuilder recAns = getStructure(grid, nr, nc, sdir, dir);
                
                ans.append(recAns);
            }
        }
        ans.append("B");
        return ans;
    }
}

// LC: 542 01 Matrix
class Solution {
    class Pair {
        int i;
        int j;
        int level;
        
        Pair(int i, int j, int level){
            this.i = i;
            this.j = j;
            this.level = level;
        }
    }
    
    public int[][] updateMatrix(int[][] mat) {
        boolean[][] visited = new boolean[mat.length][mat[0].length];
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        
        int[][] ans = new int[mat.length][mat[0].length];
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                if(mat[i][j] == 0){
                    queue.add(new Pair(i, j, 0));
                }
            }
        }
        
        while(queue.size() > 0){
            Pair rem = queue.removeFirst();
            
            if(visited[rem.i][rem.j] == true){
                continue;
            }
            visited[rem.i][rem.j] = true;
            
            ans[rem.i][rem.j] = rem.level;
            
            addN(rem.i - 1, rem.j, rem.level + 1, mat, visited, queue);
            addN(rem.i + 1, rem.j, rem.level + 1, mat, visited, queue);
            addN(rem.i, rem.j - 1, rem.level + 1, mat, visited, queue);
            addN(rem.i, rem.j + 1, rem.level + 1, mat, visited, queue);
        }
        
        return ans;
    }
    
    void addN(int i, int j, int level, int[][] mat, boolean[][] visited, ArrayDeque<Pair> queue){
        if(i < 0 || j < 0 || i >= visited.length || j >= visited[0].length 
           || visited[i][j] == true || mat[i][j] == 0){
            return;
        }
        
        queue.add(new Pair(i, j, level));
    }
    
}

// LC: 1162 As far from land as possible
class Solution {
    class Pair {
        int i;
        int j;
        int level;
        
        Pair(int i, int j, int level){
            this.i = i;
            this.j = j;
            this.level = level;
        }
    }
    public int maxDistance(int[][] mat) {
        boolean[][] visited = new boolean[mat.length][mat[0].length];
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        int[][] ans = new int[mat.length][mat[0].length];
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                if(mat[i][j] == 1){
                    queue.add(new Pair(i, j, 0));
                }
            }
        }
        
        int max = 0;
        
        while(queue.size() > 0){
            Pair rem = queue.removeFirst();
            
            if(visited[rem.i][rem.j] == true){
                continue;
            }
            visited[rem.i][rem.j] = true;
            
            // if(rem.level > max){
                max = rem.level;
            // }
            ans[rem.i][rem.j] = rem.level;
            
            addN(rem.i - 1, rem.j, rem.level + 1, mat, visited, queue);
            addN(rem.i + 1, rem.j, rem.level + 1, mat, visited, queue);
            addN(rem.i, rem.j - 1, rem.level + 1, mat, visited, queue);
            addN(rem.i, rem.j + 1, rem.level + 1, mat, visited, queue);
        }
        
        return max == 0 ? -1 : max;
    }
    
    void addN(int i, int j, int level, int[][] mat, boolean[][] visited, ArrayDeque<Pair> queue){
        if(i < 0 || j < 0 || i >= visited.length || j >= visited[0].length 
           || visited[i][j] == true || mat[i][j] == 1){
            return;
        }
        
        queue.add(new Pair(i, j, level));
    }
}

// LC: 778 Swim in rising water
class Solution {
    class Pair implements Comparable<Pair> {
        int i;
        int j;
        int tesf;
        
        Pair(int i, int j, int tesf){
            this.i = i;
            this.j = j;
            this.tesf = tesf;
        }
        
        public int compareTo(Pair o){
            return this.tesf - o.tesf;
        }
    }
    
    public int swimInWater(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        
        pq.add(new Pair(0, 0, grid[0][0]));
        while(pq.size() > 0){
            Pair rem = pq.remove();
            
            if(visited[rem.i][rem.j] == true){
                continue;
            }
            visited[rem.i][rem.j] = true;
            
            if(rem.i == grid.length - 1 && rem.j == grid[0].length - 1){
                return rem.tesf;
            }
            
            addN(rem.i - 1, rem.j, rem.tesf, grid, visited, pq);
            addN(rem.i + 1, rem.j, rem.tesf, grid, visited, pq);
            addN(rem.i, rem.j - 1, rem.tesf, grid, visited, pq);
            addN(rem.i, rem.j + 1, rem.tesf, grid, visited, pq);
        }
        
        return -1;
    }
    
    public void addN(int i, int j, int ot, int[][] grid, boolean[][] visited, PriorityQueue<Pair> pq){
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || visited[i][j] == true){
            return;
        }
        
        pq.add(new Pair(i, j, Math.max(ot, grid[i][j])));
    }
    
}

//Graphs -> Prims = https://pepcoding.com/resources/online-java-foundation/graphs/minimum-wire-to-connect-all-pcs-official/ojquestion

// LC : 1034 Coloring a Border
class Solution {
    class Pair {
        int i;
        int j;
        boolean border;
        
        Pair(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
    
    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        
        Pair p = new Pair(row, col);
        p.border = isBorder(grid, row, col);
        queue.add(p);
        
        ArrayList<Pair> res = new ArrayList<>();
        int orgc = grid[row][col];
        
        while(queue.size() > 0){
            Pair rem = queue.remove();
            
            if(visited[rem.i][rem.j]){
                continue;
            }
            visited[rem.i][rem.j] = true;
            
            if(rem.border){
                res.add(rem);
            }
            
            addN(grid, visited, queue, orgc, rem.i - 1, rem.j);
            addN(grid, visited, queue, orgc, rem.i + 1, rem.j);
            addN(grid, visited, queue, orgc, rem.i, rem.j - 1);
            addN(grid, visited, queue, orgc, rem.i, rem.j + 1);
        }
        
        for(Pair pair: res){
            grid[pair.i][pair.j] = color;
        }
        
        return grid;
    }
    
    public void addN(int[][] grid, boolean[][] visited, ArrayDeque<Pair> queue, int orgc, int i, int j){
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || visited[i][j] == true || grid[i][j] != orgc){
            return;
        }
        
        Pair p = new Pair(i, j);
        p.border = isBorder(grid, i, j);
        queue.add(p);
    }
    
    
    public boolean isBorder(int[][] grid, int i, int j){
        if(i == 0){
            return true;
        } else if(i == grid.length - 1){
            return true;
        } else if(j == 0){
            return true;
        } else if(j == grid[0].length - 1){
            return true;
        } else {
            int color = grid[i][j];
            if(grid[i - 1][j] != color){
                return true;
            } else if(grid[i + 1][j] != color){
                return true;
            } else if(grid[i][j - 1] != color){
                return true;
            } else if(grid[i][j + 1] != color){
                return true;
            } else {
                return false;
            }
        }
    }
}

// LC : 934 Shortest Bridge
class Solution {
    class Pair {
        int i;
        int j;
        int level;
        
        Pair(int i, int j, int level){
            this.i = i;
            this.j = j;
            this.level = level;
        }
    }
    
    
    public int shortestBridge(int[][] grid) {
        
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        
        boolean found = false;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1){
                    dfs(grid, queue, i, j);
                    found = true;
                    break;
                }
            }
            
            if(found){
                break;
            }
        }
        
        while(queue.size() > 0){
            Pair rem = queue.remove();
            
            if(grid[rem.i][rem.j] == -2){
                continue;
            }
            
            if(grid[rem.i][rem.j] == 1){
                return rem.level - 1;
            }
            
            grid[rem.i][rem.j] = -2;
            
            addN(grid, queue, rem.i - 1, rem.j, rem.level + 1);
            addN(grid, queue, rem.i + 1, rem.j, rem.level + 1);
            addN(grid, queue, rem.i, rem.j - 1, rem.level + 1);
            addN(grid, queue, rem.i, rem.j + 1, rem.level + 1);
        }
        
        return -1;
    }
    
    public void addN(int[][] grid, ArrayDeque<Pair> queue, int i, int j, int level){
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length){
            return;
        } else if(grid[i][j] == -1){
            return;
        } else if(grid[i][j] == -2){
            return;
        }
        
        queue.add(new Pair(i, j, level));
    }
    
    public void dfs(int[][] grid, ArrayDeque<Pair> queue, int i, int j){
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0 || grid[i][j] == -1){
            return;
        }
        
        grid[i][j] = -1;
        queue.add(new Pair(i, j, 0));
        dfs(grid, queue, i - 1, j);
        dfs(grid, queue, i + 1, j);
        dfs(grid, queue, i, j - 1);
        dfs(grid, queue, i, j + 1);
    }
}

// Gfg strongly connected component (Kosaraju)
// We can't take preorder or queue because in that destination may occur first after traversing the edges.
class Solution
{
    //Function to find number of strongly connected components in the graph.
    public int kosaraju(int V, ArrayList<ArrayList<Integer>> adj)
    {
        boolean[] vis1 = new boolean[V];
        Stack<Integer> st = new Stack<>();
        
        for(int v = 0; v < V; v++){
            if(!vis1[v]){
                dfs1(v, adj, vis1, st);
            }
        }
        
        // transpose
        ArrayList<ArrayList<Integer>> tsp = new ArrayList<>();
        for(int v = 0; v < V; v++){
            tsp.add(new ArrayList<>());
        }
        
        for(int v = 0; v < V; v++){
            for(int nbr: adj.get(v)){
                tsp.get(nbr).add(v);
            }
        }
        
        // dfs2
        int count = 0;
        boolean[] vis2 = new boolean[V];
        while(st.size() > 0){
            int v = st.pop();
            
            if(!vis2[v]){
                dfs2(v, tsp, vis2);
                count++;
            }
        }
        
        return count;
    }
    
    public void dfs2(int v, ArrayList<ArrayList<Integer>> adj, boolean[] visited){
        visited[v] = true;
        for(int nbr: adj.get(v)){
            if(!visited[nbr]){
                dfs2(nbr, adj, visited);
            }
        }
    }
    
    public void dfs1(int v, ArrayList<ArrayList<Integer>> adj, boolean[] visited, Stack<Integer> st){
        
        visited[v] = true;
        for(int nbr: adj.get(v)){
            if(!visited[nbr]){
                dfs1(nbr, adj, visited, st);
            }
        }
        
        st.push(v);
    }
}

// GFG mother vertex
class Solution
{
    //Function to find a Mother Vertex in the Graph.
    public int findMotherVertex(int V, ArrayList<ArrayList<Integer>>adj)
    {
        // Code here
        int mv = -1;
        boolean[] vis = new boolean[V];
        for(int v = 0; v < V; v++){
            if(!vis[v]){
                dfs(v, vis, adj);
                mv = v;         // we want source at upper level, we can use also stack and add in post order to store source at peek because it can be a mother vertex
            }
        }
        
        Arrays.fill(vis, false);
        dfs(mv, vis, adj);
        
        for(int v = 0; v < V; v++){
            if(vis[v] == false){
                return -1;
            }
        }
        
        return mv;
    }
    
    public void dfs(int v, boolean[] vis, ArrayList<ArrayList<Integer>> adj){
        vis[v] = true;
        for(int n: adj.get(v)){
            if(!vis[n]){
                dfs(n, vis, adj);
            }
        }
    }
}

// LC: 133 Clone Graph
class Solution {
    public Node cloneGraph(Node node) {
        if(node == null){
            return null;
        }
        HashMap<Integer, Node> visited = new HashMap<>();
        return helper(node, visited);
    }
    
    public Node helper(Node node, HashMap<Integer, Node> visited){
        Node nodeClone = new Node(node.val);
        visited.put(node.val, nodeClone);
        
        for(Node nbr : node.neighbors){
            if(!visited.containsKey(nbr.val)){
                Node nbrClone = helper(nbr, visited);
                nodeClone.neighbors.add(nbrClone);
            }else{
                Node nbrClone = visited.get(nbr.val);
                nodeClone.neighbors.add(nbrClone);
            }
        }
        return nodeClone;        
    }
}

// GFG Alien Dictionary
class Solution
{
    public String findOrder(String [] dict, int N, int K)
    {
        // Write your code here
        ArrayList<Integer>[] graph = (ArrayList<Integer>[])new ArrayList[K];
        for(int i = 0; i < K; i++){
            graph[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < dict.length - 1; i++){
            String word1 = dict[i];
            String word2 = dict[i + 1];
            
            for(int j = 0; j < Math.min(word1.length(), word2.length()); j++){
                char ch1 = word1.charAt(j);
                char ch2 = word2.charAt(j);
                
                if(ch1 != ch2){
                    graph[ch1 - 'a'].add(ch2 - 'a');
                    break;
                }
            }
        }
        
        boolean[] vis = new boolean[K];
        Stack<Integer> st = new Stack<>();
        
        
        for(int i = 0; i < K; i++){
            if(!vis[i]){
                tsort(graph, vis, st, i);
            }
        }
     
        String ans = "";
        while(st.size() > 0){
            ans += (char)(st.pop() + 'a');
        }
        
        // System.out.println(ans);
        
        return ans;
    }
    
    public void tsort(ArrayList<Integer>[] graph, boolean[] vis, Stack<Integer> st, int v){
        vis[v] = true;
        for(int n: graph[v]){
            if(!vis[n]){
                tsort(graph, vis, st, n);
            }
        }
        
        st.push(v);
    }
}

// GFG Topological based indegree based solution (Kanh's Algorithm)
class Solution
{
    //Function to return list containing vertices in Topological order. 
    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj) 
    {
        // add your code here
        int[] tsort = new int[V];
        
        int[] inDegree = new int[V];
        for(int v = 0; v < V; v++){
            for(int n: adj.get(v)){
                inDegree[n]++;
            }
        }
        
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for(int v = 0; v < V; v++){
            if(inDegree[v] == 0){
                queue.add(v);
            }
        }
        
        int idx = 0;
        while(queue.size() > 0){
            int v = queue.remove();
            tsort[idx++] = v;
            
            for(int n: adj.get(v)){
                inDegree[n]--;
                if(inDegree[n] == 0){
                    queue.add(n);
                }
            }
        }
        
        return tsort;
    }
}

// LC : 210 Course Schedule II
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] ans = new int[numCourses];
        
        int[] in = new int[numCourses];
        for(int[] edge: prerequisites){
            in[edge[1]]++;
        }
        
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for(int course = 0; course < numCourses; course++){
            if(in[course] == 0){
                queue.add(course);
            }
        }
        
        int idx = ans.length - 1;
        while(queue.size() > 0){
            int course = queue.remove();
            ans[idx--] = course;
            
            for(int[] edge: prerequisites){
                if(edge[0] == course){
                    in[edge[1]]--;
                    
                    if(in[edge[1]] == 0){
                        queue.add(edge[1]);
                    }
                }
            }
        }
        
        if(idx != -1){
            return new int[]{};
        }
        
        return ans;
    }
}

// Kruskal's Algorithm
public static void kruskals(ArrayList<Edge>[] graph){
    PriorityQueue<Edge> pq = new PriorityQueue<>();
    for(int v = 0; v < graph.length; v++){
        for(Edge e: graph[v]){
            pq.add(e);
        }
    }
    
    parent = new int[graph.length];
    rank = new int[graph.length];
    for(int i = 0; i < graph.length; i++){
        parent[i] = i;
        rank[i] = 0;
    }
    
    while(pq.size() > 0){
        Edge e = pq.remove();
        
        int srcLead = find(e.src);
        int nbrLead = find(e.nbr);
        
        if(srcLead != nbrLead){
            System.out.println(e.src + "-" + e.nbr + "@" + e.wt);
            
            union(srcLead, nbrLead);
        }
    }
}

static int[] parent;
static int[] rank;

public static int find(int x){
    if(parent[x] == x){
        return x;
    } else {
        parent[x] = find(parent[x]);
        return parent[x];
    }
}

public static void union(int s1l, int s2l){
    if(rank[s1l] < rank[s2l]){
        parent[s1l] = s2l;
    } else if(rank[s2l] < rank[s1l]){
        parent[s2l] = s1l;
    } else {
        parent[s1l] = s2l;
        rank[s2l]++;
    }
}

// LC : 305 No. of Islands 2
class Solution {
    
    public  List<Integer> numIslands2(int m, int n, int[][] positions) {
    int[][] grid = new int[m][n];
    parent = new int[m * n];
    rank = new int[m * n];
    for(int i = 0; i < m * n; i++){
        parent[i] = i;
        rank[i] = 0;
    }
    ArrayList<Integer> res = new ArrayList<>();
    count = 0;
    
    for(int[] pos: positions){
        int x = pos[0];
        int y = pos[1];
        
        if(grid[x][y] == 0){    // Here hashset would not work for checking contains function after making pair class 
            count++;
        }
        
        
        grid[x][y] = 1;
        
        handleNewCell(x, y, x - 1, y, m, n, grid);
        handleNewCell(x, y, x + 1, y, m, n, grid);
        handleNewCell(x, y, x, y - 1, m, n, grid);
        handleNewCell(x, y, x, y + 1, m, n, grid);
        
        res.add(count);
    }
    
    return res;
  }
  
  public void handleNewCell(int x, int y, int xx, int yy, int m, int n, int[][] grid){
      if(xx < 0 || yy < 0 || xx >= m || yy >= n || grid[xx][yy] == 0){
          return;
      }
      
      int xyCell = x * n + y;
      int xxyyCell = xx * n + yy;
      
      int xylead = find(xyCell);
      int xxyylead = find(xxyyCell);
      
      if(xylead != xxyylead){
          count--;
          union(xylead, xxyylead);
      }
  }
  
   int count;
   int[] parent;
   int[] rank;
  
  
  public int find(int i){
      if(parent[i] == i){
          return i;
      } else {
          parent[i] = find(parent[i]);
          return parent[i];
      }
  }
  
  public void union(int i, int j){
      if(rank[i] < rank[j]){
          parent[i] = j;
      } else if(rank[j] < rank[i]){
          parent[j] = i;
      } else {
          parent[i] = j;
          rank[j]++;
      }
  }
}

// LC : 684 Redundant Connection
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        parent = new int[edges.length + 1];
        rank = new int[edges.length + 1];
        
        for(int i = 0; i < parent.length; i++){
            parent[i] = i;
            rank[i] = 0;
        }
        
        for(int[] e: edges){
            int xl = find(e[0]);
            int yl = find(e[1]);
            
            if(xl != yl){
                union(xl, yl);
            } else {
                return e;
            }
        }
        
        return null;
    }
    
    int[] parent;
    int[] rank;
    
    public int find(int x){
        if(parent[x] == x){
            return x;
        } else {
            parent[x] = find(parent[x]);
            return parent[x];
        }
    }
    
    public void union(int xl, int yl){
        if(rank[xl] < rank[yl]){
            parent[xl] = yl;
        } else if(rank[yl] < rank[xl]){
            parent[yl] = xl;
        } else {
            parent[xl] = yl;
            rank[yl]++;
        }
    }
}