/**
 * @format
 */
import 'react-native';
import React from 'react';
import {Text} from 'react-native'
import {shallow} from 'enzyme';

import App from '../src/App';

describe('App', () => {
    const wrapper = shallow(<App></App>);

    it('is Text visible ?', () => {
        expect(wrapper.find('Text').contains('ToDo TDD')).toBe(true);
    });

    it('is AddToDo visible ?', () => {
        expect(wrapper.find('AddToDo')).toHaveLength(1);
    });

    it('is ToDoList visible ?', () => {
        expect(wrapper.find('ToDoList')).toHaveLength(1);
    });
});