// pages/course/details/details.js
const request  = require("../../../utils/request");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    category: null,
    course: null,
    customServicePhoneNo: null,
    customService: "customer_service"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let category = options.category
    console.log("category: " + category)
    let courseId = options.courseId
    let customService = this.data.customService

    this.setData({
      category: category
    })

    request.get(`courses/${courseId}`).then(res => {
      this.setData({
        course: res.data
      })
    })

    request.get(`config/${customService}`).then(res => {
      this.setData({
        customServicePhoneNo: res.data.value
      })
    })
  }, // end of onLoad function

  contact: function() {
    let phone = this.data.customServicePhoneNo || "18998815018"
    wx.makePhoneCall({
      phoneNumber: phone,
      fail: () => {
        wx.showToast({
          title: '联系客服失败，请稍后重试！',
          icon: 'none'
        });
      }
    });
      
  }, // end of contact function

  /** the purchase function */
  purchase: function() {
    let userId = wx.getStorageSync('token')
    let courseId = this.data.course.id

    // request to prepay and invoke to pay this course
    request.post(`payment/course`, {
      userId: userId,
      courseId: courseId
    }).then(res => {
      console.log("uniform order succeed, with result: " + res)
      wx.requestPayment({
        timeStamp: res.data.timeStamp,
        nonceStr: res.data.nonce,
        package: res.data.pckage,
        signType: res.data.signType,
        paySign: res.data.sign,
        success: (result)=>{
          wx.showToast({
            title: '支付成功！',
            icon: 'success',
            duration: 3000,
          });
        },
        fail: ()=>{
          wx.showToast({
            title: '支付失败，请稍后重试！',
            icon: 'none',
            duration: 3000,
          });
        }
      });
    })
  }
})