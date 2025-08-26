import React, { useState, useEffect } from 'react';
import { LogOut, Plus, TrendingUp, TrendingDown, Edit, Trash2 } from 'lucide-react';
import TransactionChart from './TransactionChart';
import AddTransaction from './AddTransaction';
import EditTransaction from './EditTransaction';

const Dashboard = ({ user, onLogout, showNotification }) => {
  const [transactions, setTransactions] = useState([]);
  const [showAddTransaction, setShowAddTransaction] = useState(false);
  const [showEditTransaction, setShowEditTransaction] = useState(false);
  const [editingTransaction, setEditingTransaction] = useState(null);
  const [summary, setSummary] = useState({ totalIncome: 0, totalExpense: 0 });

  useEffect(() => {
    fetchTransactions();
  }, []);

  const fetchTransactions = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/transactions/user/${user.id}`);
      if (response.ok) {
        const data = await response.json();
        setTransactions(data);

        // Calculate summary
        const income = data.filter(t => t.type === 'INCOME').reduce((sum, t) => sum + t.amount, 0);
        const expense = data.filter(t => t.type === 'EXPENSE').reduce((sum, t) => sum + t.amount, 0);
        setSummary({ totalIncome: income, totalExpense: expense });
      }
    } catch (error) {
      console.error('Error fetching transactions:', error);
    }
  };

  const handleTransactionAdded = () => {
    fetchTransactions();
    setShowAddTransaction(false);
  };

  const handleTransactionUpdated = () => {
    fetchTransactions();
    setShowEditTransaction(false);
    setEditingTransaction(null);
  };

  const handleEditTransaction = (transaction) => {
    setEditingTransaction(transaction);
    setShowEditTransaction(true);
  };

  const handleDeleteTransaction = async (transactionId) => {
    if (window.confirm('Are you sure you want to delete this transaction?')) {
      try {
        const response = await fetch(`http://localhost:8080/api/transactions/${transactionId}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          showNotification('Transaction deleted successfully!', 'success');
          fetchTransactions();
        } else {
          const errorData = await response.json();
          showNotification(errorData.error || 'Failed to delete transaction', 'error');
        }
      } catch (error) {
        showNotification('Error deleting transaction', 'error');
      }
    }
  };

  return (
    <div className="min-h-screen" style={{ backgroundColor: '#ADD8E6' }}>
      {/* Header */}
      <div className="bg-white shadow-sm">
        <div className="max-w-6xl mx-auto px-4 py-4">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">Expenso - Finance Tracker</h1>
              <p className="text-gray-600">Welcome back, {user.name}!</p>
            </div>
            <button
              onClick={onLogout}
              className="flex items-center space-x-2 text-gray-600 hover:text-gray-800"
            >
              <LogOut className="h-5 w-5" />
              <span>Logout</span>
            </button>
          </div>
        </div>
      </div>

      <div className="max-w-6xl mx-auto px-4 py-8">
        {/* Summary */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white p-6 rounded-lg shadow-sm">
            <div className="flex items-center">
              <div className="p-3 rounded-full bg-green-100">
                <TrendingUp className="h-6 w-6 text-green-600" />
              </div>
              <div className="ml-4">
                <p className="text-sm font-medium text-gray-600">Total Income</p>
                <p className="text-2xl font-bold text-green-600">₹{summary.totalIncome.toFixed(2)}</p>
              </div>
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-sm">
            <div className="flex items-center">
              <div className="p-3 rounded-full bg-red-100">
                <TrendingDown className="h-6 w-6 text-red-600" />
              </div>
              <div className="ml-4">
                <p className="text-sm font-medium text-gray-600">Total Expense</p>
                <p className="text-2xl font-bold text-red-600">₹{summary.totalExpense.toFixed(2)}</p>
              </div>
            </div>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-sm">
            <div className="flex items-center">
              <div className="p-3 rounded-full bg-blue-100">
                <div className="h-6 w-6 bg-blue-600 rounded-full"></div>
              </div>
              <div className="ml-4">
                <p className="text-sm font-medium text-gray-600">Net Balance</p>
                <p className={`text-2xl font-bold ${(summary.totalIncome - summary.totalExpense) >= 0 ? 'text-green-600' : 'text-red-600'}`}>
                  ₹{(summary.totalIncome - summary.totalExpense).toFixed(2)}
                </p>
              </div>
            </div>
          </div>
        </div>

        {/* Chart and Transactions */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Chart */}
          <div className="bg-white p-6 rounded-lg shadow-sm">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">Income vs Expense</h3>
            <TransactionChart income={summary.totalIncome} expense={summary.totalExpense} />
          </div>

          {/* Recent Transactions */}
          <div className="bg-white p-6 rounded-lg shadow-sm">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-semibold text-gray-900">Recent Transactions</h3>
              <button
                onClick={() => setShowAddTransaction(true)}
                className="flex items-center space-x-2 bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
              >
                <Plus className="h-4 w-4" />
                <span>Add Transaction</span>
              </button>
            </div>

            <div className="space-y-3 max-h-96 overflow-y-auto">
              {transactions.length === 0 ? (
                <p className="text-gray-500 text-center py-4">No transactions yet</p>
              ) : (
                transactions.slice(-10).reverse().map((transaction) => (
                  <div key={transaction.id} className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                    <div className="flex-1">
                      <p className="font-medium text-gray-900">{transaction.category}</p>
                      <p className="text-sm text-gray-600">{transaction.description}</p>
                    </div>
                    <div className="text-right">
                      <p className={`font-semibold ${transaction.type === 'INCOME' ? 'text-green-600' : 'text-red-600'}`}>
                        {transaction.type === 'INCOME' ? '+' : '-'}₹{transaction.amount.toFixed(2)}
                      </p>
                      <p className="text-sm text-gray-500">{new Date(transaction.date).toLocaleDateString()}</p>
                    </div>
                    <div className="flex space-x-2 ml-3">
                      <button
                        onClick={() => handleEditTransaction(transaction)}
                        className="text-blue-600 hover:text-blue-800 p-1"
                        title="Edit transaction"
                      >
                        <Edit className="h-4 w-4" />
                      </button>
                      <button
                        onClick={() => handleDeleteTransaction(transaction.id)}
                        className="text-red-600 hover:text-red-800 p-1"
                        title="Delete transaction"
                      >
                        <Trash2 className="h-4 w-4" />
                      </button>
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>
        </div>
      </div>

      {/* Add Transaction Modal */}
      {showAddTransaction && (
        <AddTransaction
          user={user}
          onClose={() => setShowAddTransaction(false)}
          onTransactionAdded={handleTransactionAdded}
          showNotification={showNotification}
        />
      )}

      {/* Edit Transaction Modal */}
      {showEditTransaction && (
        <EditTransaction
          user={user}
          transaction={editingTransaction}
          onClose={() => {
            setShowEditTransaction(false);
            setEditingTransaction(null);
          }}
          onTransactionUpdated={handleTransactionUpdated}
          showNotification={showNotification}
        />
      )}
    </div>
  );
};

export default Dashboard;