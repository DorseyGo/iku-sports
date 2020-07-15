const request = require("../../utils/request");

// pages/order/order.js
const orderStatuses = [
  {
    id: 1,
    displayName: "全部",
    status: 9
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
    current: 9, // current status, 9 for all
    curPage: 1, // current page
    orders: [],
    hasData: true, // whether has data
    orderId2Removed: null,
    hasMore: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.loadOrders()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.setData({
      curPage: 1
    })

    this.loadOrders()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (hasMore) {
      wx.showLoading({
        title: "正在加载...",
        mask: true
      });

      this.loadOrders()
      return
    }

    wx.showLoading({
      title: "已经到底了!",
      mask: true
    });
  },
  
  selectTab: function(e) {
    let curStatus = e.currentTarget.dataset.cur
    this.setData({
      current: curStatus
    });

    this.loadOrders()
  },

  loadOrders: function() {
    let userId = wx.getStorageSync('token')
    let curPage = this.data.curPage
    request.get(`orders/${curPage}`, {
      userId: userId,
      status: this.data.current
    }).then(res => {
      this.setData({
        orders: res.data.data,
        hasData: res.data.data.length > 0,
        hasMore: res.data.hasNextPaging
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
        wx.showToast({
          title: '订单已删除',
          icon: 'success'
        });

        this.removeOrderById(orderId)
    })
  },

  removeOrderById: function(orderId) {
    let pos = this.data.orders.forEach((item, index) => {
      if (item.id === orderId) {
        return index;
      }

      return -1;
    });

    if (pos != -1) {
      this.data.orders.splice(pos, 1)
    }
  }
})