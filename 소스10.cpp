#include <stack>
#include <iostream>
#include <vector>
#include <malloc.h>
#include <queue>
using namespace std;

int n;
int num = 1;
int i = 0;
bool flag = true;

int main() {
	stack<int> st;
	queue<char> Queue;
	cin >> n;

	//num sequence 
	int *numPtr = (int *)malloc(sizeof(int)*n);
	for (i = 0; i < n; i++) {
		cin >> numPtr[i];
	}
	//first push 
	for (i = 1; i <= numPtr[0]; i++) {
		st.push(num);
		num++;
		Queue.push('+');
	}
	//cout << "top value : " << st.top() << endl;

	i = 0;
	while (flag) {
		//cout << "top value : " << st.top() << endl;
		if (st.empty() == 0 && st.top() == numPtr[i]) {
			st.pop();
			Queue.push('-');
			i++;
			//cout << "i: pop count " <<i << endl;
		}
		else {
			st.push(num);
			Queue.push('+');
			//cout << "num value " << num << endl;
			num++;
			
		}

		if (st.empty() == 1 && i == n){
			//cout << "really here? " << endl;
			flag = false;
			while (!Queue.empty()) 
			{
				cout << Queue.front() << '\n';
				Queue.pop();
			}
		}
		if (st.empty() == 0 && num-1>n) {
			flag = false;
			cout << "NO";
		}
	}



}