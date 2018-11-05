import {courseService} from '../services'
import generalMutations from '../helpers/store-mutations'
import Vue from 'vue';

const state = {
  items: []
}

const getters = {
  coursesByTeacherId: (state) => (teacherId) => {
    return state.items.filter(i => i.teacherId === teacherId);
  }
}

const actions = {
  create({commit}, course) {
    return new Promise((resolve, reject) => {
      commit('createRequest', course);

      courseService.create(course)
        .then(c => {
          commit('createSuccess', c);
          resolve(c);
        })
        .catch(e => {
          commit('createFailure', e);
          reject(e);
        });
    });
  },
  get({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getRequest', id);

      courseService.getById(id)
        .then(c => {
          commit('getSuccess', c);
          resolve(c);
        })
        .catch(e => {
          commit('getFailure', {id, error: e.toString()});
          reject(e);
        });
    });
  },
  getByTeacherId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByTeacherIdRequest');

      courseService.getByTeacherId(id)
        .then(courses => {
          commit('getByTeacherIdSuccess', {teacherId: id, courses});
          resolve(courses);
        })
        .catch(e => {
          commit('getByTeacherIdFailure', e);
          reject(e);
        });
    });
  },
  delete({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('deleteRequest', id);

      courseService.delete(id)
        .then(() => {
          commit('deleteSuccess', id);
          resolve();
        })
        .catch(e => {
          commit('deleteFailure', {id, error: e.toString()});
          reject(e);
        });
    });
  }
}

const mutations = {
  ...generalMutations,
  getByTeacherIdRequest(state) {
  },
  getByTeacherIdSuccess(state, {teacherId, courses}) {
    courses.forEach(course => {
      const idx = state.items.findIndex(c => c.id === course.id);
      if (idx === -1) {
        state.items.push({...course, teacherId});
      } else {
        Vue.set(state.items, idx, {...course, teacherId});
      }
    });
  },
  getByTeacherIdFailure(state, id) {
  }

}

export const courses = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}