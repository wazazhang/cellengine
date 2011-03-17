package com.g2d.display.ui;


abstract public class TextBoxNumber<T extends Number> extends TextBoxSingle
{
	public TextBoxNumber() {
		super("0");
	}
	
	abstract public T getValue();
	
	abstract public void setValue(T value);

	protected String formatText(char insert, String prev_text, String new_text) {
		return new_text;
	}
	
	@Override
	public void insertCharAtCurrentCaret(char ch) {
		String prev_text = getText();
		try {
			super.insertCharAtCurrentCaret(ch);
			String new_text = formatText(ch, prev_text, super.getText());
			super.setText(new_text);
		} catch (Exception err) {
			super.setText(prev_text);
		}
	}
	
	public static class Integer extends TextBoxNumber<java.lang.Integer>
	{
		public Integer() {}
		public Integer(int init) {
			this.setValue(init);
		}
		@Override
		public java.lang.Integer getValue() {
			String prev_text = getText();
			return new java.lang.Integer(prev_text);
		}
		
		@Override
		public void setValue(java.lang.Integer value) {
			super.setText(value+"");
		}
		
		@Override
		public void setText(String text) {
			super.setText(new java.lang.Integer(text)+"");
		}
		
		@Override
		protected String formatText(char insert, String prev_text, String new_text) {
			if (new_text.isEmpty()) {
				return "0";
			}
			return java.lang.Integer.parseInt(new_text)+"";
		}

	}
}