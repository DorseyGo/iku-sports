const request = require("../../utils/request");

// pages/order/order.js
const orderStatuses = [
  {
    id: 1,
    displayName: "全部",
    status: -1
  },
  {
    id: 2,
    displayName: "待付款",
    status: 0
  },
  {
    id: 3,
    displayName: "已完成",
    status: 1
  },
  {
    id: 4,
    displayName: "已取消",
    status: 3
  }
]

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderStatues: orderStatuses,
    current: -1,
    offset: 0,
    orders: [],
    hasData: true,
    orderId2Removed: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },
  
  selectTab: function(e) {
    let curStatus = e.currentTarget.dataset.cur
    this.setData({
      current: curStatus
    });
  },

  loadOrders: function(status) {
    let userId = wx.getStorageSync('token');
    request.get(`orders`, {
      userId: userId,
      status: status,
      offset: offset
    }).then(res => {
      this.setData({
        orders: res.data,
        hasData: res.data.length > 0
      })
    })
  },

  delete: function(e) {
    let orderId = e.currentTarget.dataset.cur
    wx.showModal({
      title: '提示',
      content: '是否删除此订单？',
      showCancel: true,
      cancelText: '取消',
      cancelColor: '#000000',
      confirmText: '确定',
      confirmColor: '#3CC51F',
      success: (result) => {
        if (result.confirm) {
          this.deleteOrder(orderId)
        }
      }
    });
  },

  deleteOrder: function(orderId) {
    request.del(`orders/${orderId}`).then(res => {
        
    })
  }
})