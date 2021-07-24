class ShortestPathFinder
{

    public static class DirectedEdge
    {
        private final int to, from, weight;

        public DirectedEdge(int to, int from, int weight)
        {
            this.to = to;
            this.from = from;
            this.weight = weight;
        }

        int weight()
        {
            return this.weight;
        }

        int to()
        {
            return this.to;
        }

        int from()
        {
            return this.from;
        }
    }

    public static class EDDigraph
    {
        private final int v_count;
        private int e_count;

        // we can use an array as the number of vertices will be constant throughout
        // each element in this array represents a vertex's outgoing edges
        private MyBST<Integer, DirectedEdge>[] edges;

        /*
         * edges[i] = i ---> k
        */


        public EDDigraph(int v_count)
        {
            this.v_count = v_count;
            this.e_count = 0;

            this.edges = new MyBST[this.v_count];

            for (int i = 0; i < v_count; i++)
                this.edges[i] = new MyBST<Integer, DirectedEdge>();
        }

        public int v()
        {
            return this.v_count;
        }

        public int e()
        {
            return this.e_count;
        }

        public Iterable<DirectedEdge> connected(int vertex)
        {
            Queue<DirectedEdge> queue = new Queue<DirectedEdge>();
            for (DirectedEdge j : this.edges[vertex].rankOrderValues())
                queue.enqueue(j);

            return queue;
        }

        public Iterable<DirectedEdge> edges()
        {
            Queue<DirectedEdge> collected_edges = new Queue<DirectedEdge>();
            MyBST<Integer, DirectedEdge> current_vertex;

            for (int i = 0; i < this.v_count; i++)
            {
                current_vertex = this.edges[i];
                for (DirectedEdge j : current_vertex.rankOrderValues())
                    collected_edges.enqueue(j);
            }

            return collected_edges;
        }

        public void addEdge(DirectedEdge edge)
        {
            MyBST<Integer, DirectedEdge> vertex_BST = edges[edge.from()];
            int current_size = vertex_BST.size();
            vertex_BST.put(current_size, edge);
            edges[edge.from()] = vertex_BST;
            this.e_count++;
        }
    }

    public static class ShortestPath
    {
        private DirectedEdge[] edgeTo; // stores the edge that connects i to the path
        private int[] distTo; // stores the min known distance from the origin to i
        private int from;
        EDDigraph fullMap;

        public ShortestPath(EDDigraph map, int from)
        {
            int size = map.v_count;
            this.distTo = new int[size];
            this.edgeTo = new DirectedEdge[size];

            this.from = from;
            this.fullMap = map;

            for (int i = 0; i < size; i++)
            {
                this.distTo[i] = i == from ? 0 : Integer.MAX_VALUE;
                this.edgeTo[i] = null;
            }

            this.dijkstra();
        }

        public int distTo(int vertex)
        {
            return this.distTo[vertex];
        }

        public boolean hasPathTo(int vertex)
        {
            return this.distTo[vertex] < Integer.MAX_VALUE;
        }

        public Iterable<DirectedEdge> pathTo(int vertex)
        {
            if (!this.hasPathTo(vertex))
                return null;

            Stack<DirectedEdge> path = new Stack<DirectedEdge>();
            DirectedEdge e = edgeTo[vertex];
            while (e != null)
            {
                path.push(e);
                e = edgeTo[e.from()];
            }
            return path;
        }

        //private boolean hasNegativeCycle = false;

        //private void bellmanFord()
        //{
        //    Queue<int[]> passes = new Queue<int[]>();

        //    int[] changed = new int[];

        //}

        //private void findNegativeCircle()
        //{
        //    int current_size = edgeTo.length;
        //    EDDigraph SPT = new EDDigraph(current_size);

        //    for (int v = 0; v < current_size; v++)
        //    {
        //        if (edgeTo[v] != null)
        //            SPT.addEdge(edgeTo[v]);
        //    }

        //    getCycles(SPT);
        //    int[] cycle = new int[current_size];

        //    int starting_vertex = 0;

        //    while (!SPT.connected(starting_vertex).isEmpty() {}&& starting_vertex < this.fullMap.v())
        //        starting_vertex++;

        //    for (DirectedEdge e : SPT.connected(starting_vertex))
        //    {
        //        e.to()
        //    }

        //}

        //private void getCycles(EDDigraph SPT)
        //{
        //    for (int i = 0; i < ...; )
        //}

        //private void newCycle(Queue<DirectedEdge> partial, EDDigraph SPT)
        //{

        //}

        private void dijkstra()
        {
            // this is our simulation of a priority queue
            // nodes are stored based on their relative distances, so that we can prioritize the closest
            // notice that distances can repeat (without necessarily pertaining to the same node)
            // so that we will use an internal queue to store all matching distances
            MyBST<Integer, Queue<Integer>> priority = new MyBST<Integer, Queue<Integer>>();

            // start our queue with the starting vertex
            Queue<Integer> entry = new Queue<Integer>();
            entry.enqueue(this.from);
            priority.put(0, entry);

            int vertex, keyval;

            // then we go through all of the vertices that are worth looking into
            while (!priority.isEmpty())
            {
                // as the BST already sorts them for us, we always look into the least node
                keyval = priority.min();
                entry = priority.get(keyval);
                // and then get whatever value is in the queue
                vertex = entry.dequeue();

                // it's very important to update the priority queue so that it eventually gets empty
                if (entry.isEmpty())
                    priority.deleteMin();
                else
                    priority.put(keyval, entry);

                // now we have a look at every connection going out from the current vertex
                // i.e. a vertex relaxation
                for (DirectedEdge e : this.fullMap.connected(vertex))
                {
                    // so, if the edge actually promoted a relaxation
                    if (this.relax(e))
                    {
                        // we want to use it
                        int distance = this.distTo[e.to()];
                        if (priority.contains(distance))
                            entry = priority.get(distance);
                        else
                            entry = new Queue<Integer>();

                        entry.enqueue(e.to());
                        priority.put(distance, entry);

                    }
                }
            }

        }

        // this returns bool to indicate whether or not this edge actually caused a relaxation
        private boolean relax(DirectedEdge e)
        {
            int A = e.from;
            int B = e.to;

            // if the distance to get to B would be decreased by changing
            // the path to go through A + e
            if (distTo[B] > distTo[A] + e.weight())
            {
                // then we update it (as in relaxing an elastic band)
                distTo[B] = distTo[A] + e.weight();
                edgeTo[B] = e;
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        int M = StdIn.readInt();
        int K = StdIn.readInt();

        EDDigraph map = new EDDigraph(N);
        int to, from, weight;

        for (int i = 0; i < M; i++)
        {
            from = StdIn.readInt();
            to = StdIn.readInt();
            weight = StdIn.readInt();

            map.addEdge(new DirectedEdge(to, from, weight));
        }

        int start_city[] = new int[K];

        for (int i = 0; i < K; i++)
            start_city[i] = StdIn.readInt();

        int F = StdIn.readInt();

        ShortestPath shortest_paths[] = new ShortestPath[K];

        for (int i = 0; i < start_city.length; i++)
            shortest_paths[i] = new ShortestPath(map, start_city[i]);

        ShortestPath rota_do_mito = new ShortestPath(map, F);


        boolean possible_cities[] = new boolean[N];
        int city_count = N;

        for (int i = 0; i < N; i++)
        {
            possible_cities[i] = true;

            for (ShortestPath p : shortest_paths)
            {
                if (p.hasPathTo(i))
                {
                    for (DirectedEdge e : p.pathTo(i))
                    {
                        if (p.distTo(e.to()) > rota_do_mito.distTo(e.to()))
                        {
                            city_count--;
                            possible_cities[i] = false;
                            break;
                        }
                    }
                    if (!possible_cities[i])
                        break;

                } else {
                    city_count--;
                    possible_cities[i] = false;
                }
            }
        }

        if (city_count != 0)
        {
            StdOut.println("O REINO ESTÁ SALVO!");
            StdOut.println(city_count);

            for (int i = 0; i < possible_cities.length; i++)
            {
                if (possible_cities[i])
                    StdOut.print(i + " ");
            }
        }
        else
            StdOut.println("INFELIZMENTE O PRECONCEITO VENCEU :(");

    }
}
