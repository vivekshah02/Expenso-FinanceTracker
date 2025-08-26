import React from 'react';

const TransactionChart = ({ income, expense }) => {
  // Simple pie chart using CSS
  const total = income + expense;
  const incomePercentage = total > 0 ? (income / total) * 100 : 50;
  const expensePercentage = total > 0 ? (expense / total) * 100 : 50;

  // If no data showing empty state
  if (income === 0 && expense === 0) {
    return (
      <div className="h-64 flex items-center justify-center text-gray-500">
        <p>No transactions to display</p>
      </div>
    );
  }

  return (
    <div className="h-64 flex flex-col items-center justify-center">
      <div className="relative w-32 h-32 rounded-full mb-4"
           style={{
             background: `conic-gradient(#10B981 0% ${incomePercentage}%, #EF4444 ${incomePercentage}% 100%)`
           }}>
        <div className="absolute inset-4 bg-white rounded-full flex items-center justify-center">
          <span className="text-sm font-semibold text-gray-700">₹{total.toFixed(0)}</span>
        </div>
      </div>

      <div className="flex space-x-6">
        <div className="flex items-center">
          <div className="w-3 h-3 bg-green-500 rounded-full mr-2"></div>
          <span className="text-sm text-gray-600">Income: ₹{income.toFixed(2)}</span>
        </div>
        <div className="flex items-center">
          <div className="w-3 h-3 bg-red-500 rounded-full mr-2"></div>
          <span className="text-sm text-gray-600">Expense: ₹{expense.toFixed(2)}</span>
        </div>
      </div>
    </div>
  );
};

export default TransactionChart;