
public class TrinaryTree {
	Integer val;
	TrinaryTree left = null;
	TrinaryTree mid = null;
	TrinaryTree right = null;
	TrinaryTree parent = null;

	public TrinaryTree(int val) {
		setVal(val);
	}

	public TrinaryTree(int[] vals) {
		this.val = vals[0];
		for (int i = 1; i < vals.length; i++) {
			insert(vals[i]);
		}
	}

	/**Inserts an integer value input into the tree
	 * @param input*/
	public void insert(int input) {
		int ownVal = getVal();
		if (input < ownVal) {
			if (hasLeft()) {
				left.insert(input);				
			} else {
				setLeft(input);
			}
		} else if (input > ownVal) {
			if (hasRight()) {
				right.insert(input);				
			} else {
				setRight(input);
			}
		} else if (input == ownVal) {
			if (hasMid()) {
				mid.insert(input);				
			} else {
				setMid(input);
			}
		}
	}

	/**Delete the bottom-most layer of the tree.  
	 * If the deleted node has 1 child, swap that node with its child, and delete that child.
	 * If the deleted node has 2 children, swap that node with its successor, and call delete on that successor.
	 * @param input
	 * */  
	public void delete(int input) {
		int ownVal = getVal();
		if (input > ownVal) {
			if (hasRight()) {
				right.delete(input);
			} else {
				throw new IllegalArgumentException("Value does not exist");
			}
		} else if (input < ownVal) {
			if (hasLeft()) {
				left.delete(input);
			} else {
				throw new IllegalArgumentException("Value does not exist");
			}
		} else if (input == ownVal) {
			if (hasMid()) {
				if (mid.isLeaf()) {
					mid = null;
				} else {
					mid.delete(input);
				}
			} else if (hasLeft() && hasRight()){				
				swapWithSuccessor();
			} else if (hasLeft()) {
				swapChild(left);
			} else if (hasRight()){
				swapChild(right);				
			} else if (isLeaf()) {
				if (parent == null) {
					this.val = null;
				} else {
					if (ownVal < parent.getVal()) {
						parent.left = null;
					} else if (ownVal > parent.getVal()) {
						parent.right = null;
					}
				}
			}
		}
	}

	/**Get the successor of the current tree, which is the rightmost node of the tree's immediate left.
	 * */
	TrinaryTree getSuccessor() {
		TrinaryTree successorTree = getLeft();
		while (successorTree.hasRight()) {
			successorTree = successorTree.getRight();
		}
		return successorTree;
	}

	/**Replace the current node with the successor node, and then call delete on the successor node.*/
	void swapWithSuccessor() {
		TrinaryTree successor = getSuccessor();
		setVal(successor.getVal());
		int midCount = successor.midCount();
		for (int i = 0; i < midCount; i++) {
			this.insert(successor.getVal());
		}
		if (getLeft() == successor) {
			setLeft(successor.getLeft());
		}
		for (int i = 0; i < midCount + 1; i++) {
			successor.delete(successor.getVal());	
		}
	}

	/**set the current tree to be the child.  
	 * This indirectly disconnects the child from the tree structure.	
	 * @param child
	 */
	private void swapChild(TrinaryTree child) {
		setVal(child.val);
		setLeft(child.getLeft());
		setMid(child.getMid());
		setRight(child.getRight());
	}

	/**Calculates the number of mid children of the current tree*/
	private int midCount() {
		int result = 0;
		if (!hasMid()) {
			return result;
		} else {
			TrinaryTree tree = getMid();
			result++;
			while (tree.hasMid()) {
				tree = tree.getMid();
				result++;
			}
			return result;
		}
	}

	void setParent(TrinaryTree parent) {
		this.parent = parent;
	}

	void setVal(int val) {
		this.val = val;
	}


	int getVal() {
		return val;
	}

	TrinaryTree getLeft() {
		return left;
	}

	TrinaryTree getMid() {
		return mid;
	}

	TrinaryTree getRight() {
		return right;
	}

	void setLeft(int i) {
		this.left = new TrinaryTree(i);
		left.parent = this;
	}

	void setLeft(TrinaryTree tree) {
		this.left = tree;
		if (tree != null) {
			left.parent = this;
		}
	}

	void setMid(int i) {
		this.mid = new TrinaryTree(i);
		mid.parent = this;
	}

	void setMid(TrinaryTree tree) {
		this.mid= tree;
		if (tree != null) {
			mid.parent = this;
		}
	}

	void setRight(int i) {
		this.right = new TrinaryTree(i);
		right.parent = this;
	}

	void setRight(TrinaryTree tree) {
		this.right = tree;
		if (tree != null) {
			right.parent = this;
		}
	}

	boolean hasLeft() {
		return left != null;
	}

	boolean hasMid() {
		return (mid != null);
	}

	boolean hasRight() {
		return right != null;
	}

	boolean isLeaf() {
		return !hasLeft() && !hasMid() && !hasRight();
	}

	void print() {
		System.out.println(val);
		if (hasLeft()) {
			System.out.println(val + " printing left");
			left.print();
		}
		if (hasMid()) {
			System.out.println(val + " printing mid");
			mid.print();
		}
		if (hasRight()) {
			System.out.println(val + " printing right");
			right.print();
		}
	}
}



