/**
 * @format
 */
import 'react-native';
import React from 'react';
import {shallow} from 'enzyme';

import AppToDo from '../src/AddToDo';

describe('Rendering', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<AppToDo></AppToDo>);
    });

    it('is TextInput visible ?', () => {
        expect(wrapper.find('TextInput')).toHaveLength(1);
    });

    it('is Button visible ?', () => {
        expect(wrapper.find('Button')).toHaveLength(1);
    });
});

describe('Interaction', () => {
    let wrapper;
    let props;
    const text = 'some ToDo';

    beforeEach(() => {
        props = {
           onAdded: jest.fn()
        };

        wrapper = shallow(<AppToDo {...props}></AppToDo>);

        wrapper.find('TextInput').simulate('changeText', 'some ToDo');
        wrapper.find('Button').prop('onPress')();
    });

    it('should call the onAdded callback with input text', () => {
        expect(props.onAdded).toHaveBeenCalledTimes(1);
        expect(props.onAdded).toHaveBeenCalledWith(text);
    });
});