import _axios from "./interceptor"

export default {
  // 방송 스케줄 관련
  // C(만들었는데 진행 안한 것) -> Y(진행중) -> N(끝)
  getBroadcastList () {
    return _axios({
      url: 'broadcast/all/C',
      method: 'get',
    })
  },
  // 방송 중인 영상 리스트
  getOnAirList () {
    return _axios({
      url: 'broadcast/all/Y',
      method: 'get',
    })
  },
  // 방송 정보 관련
  getBroadcastDetail (id) {
    return _axios({
      url: `broadcast/${id}`,
      method: 'get'
    })
  },
  // 방송 생성
  createBroadcast (data) {
    return _axios({
      url: 'broadcast',
      method: 'post',
      data: data
    })
  },
  // 방송 수정
  updateBroadcastInfo (broadcastInfo) {
    return _axios({
      url: 'broadcast',
      method: 'patch',
      data: broadcastInfo
    })
  },
  // 방송 삭제
  deleteBroadcast (id) {
    return _axios({
      url: `broadcast/${id}`,
      method: 'delete',
    })
  },
  // 참석 명단
  getBroadcastStudents (id) {
    return _axios({
      url: `broadcast/attendance/${id}`,
      method: 'get',
    })
  },
}