// pages/item-list/item-list.js
const request =  require("../../utils/request");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsList: [],
    // 是否还有下一页数据
    hasNextPage: false,
  },

  /**
   * 商品列表请求参数
   */
  goodsQuery: {
    pageNum: 1,
    pageSize: 10
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getGoodsList();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 获取商品列表
   */
  getGoodsList: function () {
    request.get("/goods/list", this.goodsQuery).then(res => {
      this.hasNextPage = res.data.hasNextPaging;
      this.setData({
        hasNextPage: this.data.hasNextPaging,
        goodsList: [...this.data.goodsList, ...res.data.data]
      })
    });
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

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
    if (this.hasNextPage) {
      this.goodsQuery.pageNum++;
      this.getGoodsList();
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})