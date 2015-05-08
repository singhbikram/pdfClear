package pdfClear;

import java.util.ArrayList;
import java.util.LinkedList;

public class LineParse implements LineParseInterface {
	private int bottomUp=1;
	private int topDown=-1;
	private int curLine=1;
	@Override
	public ArrayList<LinkedList<Integer>> sortBlock(ArrayList<Word> wordList,
			int[] included) {
			int height=wordList.size();	
			ArrayList<LinkedList<Integer>> m=new ArrayList<LinkedList<Integer>>();
			m.add(new LinkedList<Integer>());
			QuickSort quick= new QuickSort();
			quick.sort(included, wordList);
			int[] id=new int[height];
			int[] midY=new int[height];
			for(int i: included){
				int y2=wordList.get(i).getBottom();//y1
				int y1=wordList.get(i).getTop();//y2
				int indexInList=getLine(y1,y2,midY,1);
				if(midY[indexInList]==0){
				midY[indexInList]=(y1+y2)/2;
				}
				if(isOccupied(y1,y2,id)){
					m.get(indexInList).add(i);
					if(id[y1]!=0&&id[y2]!=0){
						continue;
					}
					if(id[y1]!=0)	uniteDirection(y2,y1-1,id,id[y1],topDown);
					if(id[y2]!=0)	uniteDirection(y1,y2+1,id,id[y2],bottomUp);
					continue;
				}
				LinkedList<Integer> link=new LinkedList<Integer>();
				if(m.size()<=indexInList){
					link.add(i);
					int indexInArray=1;
					while(true){
						if(indexInArray>=m.size()){
							m.add(link);
							break;
						}
						int indexOfLinkedList=m.get(indexInArray).getFirst();
						if(wordList.get(indexOfLinkedList).getTop()>=wordList.get(link.getFirst()).getTop())	{
							m.add(indexInArray, link);
							break;
						}
						indexInArray++;
					}
				}else{
					m.get(indexInList).add(i);
				}
				unite(y1,y2,id,indexInList);
				
			}
			return m;
			
	}
	/*
	public ArrayList<LinkedList<Integer>> test(int h,ArrayList<Word> array,int[] index){
		height=h;
		ArrayList<LinkedList<Integer>> m=new ArrayList<LinkedList<Integer>>();
		m.add(new LinkedList<Integer>());
		QuickSort quick= new QuickSort();
		quick.sort(index, array);
		for(int i:index)
		System.out.print(i);
		System.out.println();
		int[] id=new int[height];
		int[] firstIndex=new int[height];
		int[] midY=new int[height];
		for(int i: index){
			int y2=array.get(i).getBottom();//y1
			int y1=array.get(i).getTop();//y2
			int indexInList=getLine(y1,y2,midY,1);
			if(midY[indexInList]==0){
			midY[indexInList]=(y1+y2)/2;
			}
			if(isOccupied(y1,y2,id)){
				m.get(indexInList).add(i);
				if(id[y1]!=0&&id[y2]!=0){
					continue;
				}
				if(id[y1]!=0)	uniteDirection(y2,y1-1,id,id[y1],topDown);
				if(id[y2]!=0)	uniteDirection(y1,y2+1,id,id[y2],bottomUp);
				continue;
			}
			LinkedList<Integer> link=new LinkedList<Integer>();
			if(m.size()<=indexInList){
				link.add(i);
				int indexInArray=1;
				while(true){
					if(indexInArray>=m.size()){
						m.add(link);
						break;
					}
					int indexOfLinkedList=m.get(indexInArray).getFirst();
					if(array.get(indexOfLinkedList).getTop()>=array.get(link.getFirst()).getTop())	{
						m.add(indexInArray, link);
						break;
					}
					indexInArray++;
				}
			}else{
				m.get(indexInList).add(i);
			}
			unite(y1,y2,id,indexInList,firstIndex);
			
		}
		return m;
		
	}*/
	private boolean isOccupied(int y1,int y2,int[] id){
		return id[y1]!=0||id[y2]!=0;
	}
	private void unite(int y1,int y2,int[] id,int index){
		
		uniteDirection(y1,y2+1,id,index,bottomUp);
		uniteDirection(y2,y1-1,id,index,topDown);
	}
	//return midY if it belongs to a line, 0 if it does not
	private int getLine(int y1,int y2,int[] midY, int id){
		if(midY[1]==0||id==midY.length) return curLine++;
		if(midY[id]>y1&&midY[id]<y2) return id;
		return getLine(y1,y2,midY,++id);
	}
	private void uniteDirection(int y,int bound,int[] id,int i,int direct){
		if(id[y]==i||y==bound) return;
		id[y]=i;
		uniteDirection(y+direct,bound,id,i,direct);
	}
	
	
	
	
	
	private class QuickSort{
		public void sort(int[] included,ArrayList<Word> wordList){
			sort(included,0,included.length-1,wordList);
		}
		private void sort(int[] included,int lo,int hi,ArrayList<Word> wordList){
			if(hi<=lo) return;
			int mid=partition(included,lo,hi,wordList);
			sort(included,lo,mid-1,wordList);
			sort(included,mid+1,hi,wordList);
		}
		private int partition(int[] included,int first,int last,ArrayList<Word> wordList){
			int i=first;
			int j=last;
			int mid=(first+last)/2;
			sortFirstMiddleLast(included,first,mid,last,wordList);
			if(last-first<=1) return first;
			swap(included,mid,last);
			Word pivot= wordList.get(included[last]);
			while(i<j){
				while(i<last&&wordList.get(included[++i]).getLeft()<pivot.getLeft());
				while(j>first&&wordList.get(included[--j]).getLeft()>=pivot.getLeft());
    				if(i<j){
					swap(included,i++,j--);
				}else break;
			}
			swap(included,last,i);
			return i;
		}
		private void swap(int[] included,int i,int j){
			included[i]=included[i]^included[j];
			included[j]=included[i]^included[j];
			included[i]=included[i]^included[j];
		}
		private void sortFirstMiddleLast(int[] included,int first,int mid,int last,ArrayList<Word> wordList){
			if(wordList.get(included[first]).getLeft()>wordList.get(included[mid]).getLeft()) swap(included,first,mid);

			if(wordList.get(included[last]).getLeft()<wordList.get(included[mid]).getLeft()) swap(included,last,mid);

			if(wordList.get(included[first]).getLeft()>wordList.get(included[mid]).getLeft()) swap(included,first,mid);

		}
	}
}
