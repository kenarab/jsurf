/*
 *    Copyright 2008 Christian Stussak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.mfo.jsurf.algebra;

public class Expand extends AbstractVisitor< XYZPolynomial, Void >
{
    private ValueCalculator valueCalculator;
    
    public Expand()
    {
        this.valueCalculator = new ValueCalculator( 0.0, 0.0, 0.0 );
    }
    
    public XYZPolynomial visit( PolynomialAddition pa, Void param )
    {
        XYZPolynomial first = pa.firstOperand.accept( this,( Void ) null );
        XYZPolynomial second = pa.secondOperand.accept( this,( Void ) null );        
        return first.add( second );
    }
    
    public XYZPolynomial visit( PolynomialSubtraction ps, Void param )
    {
        XYZPolynomial first = ps.firstOperand.accept( this,( Void ) null );
        XYZPolynomial second = ps.secondOperand.accept( this,( Void ) null );
        
        return first.sub( second );
    }
    
    public XYZPolynomial visit( PolynomialMultiplication pm, Void param )
    {
        XYZPolynomial first = pm.firstOperand.accept( this,( Void ) null );
        XYZPolynomial second = pm.secondOperand.accept( this,( Void ) null );
        
        return first.mult( second );
    }
    
    public XYZPolynomial visit( PolynomialPower pp, Void param )
    {
        XYZPolynomial base = pp.base.accept( this,( Void ) null );        
        return base.pow( pp.exponent );
    }
    
    public XYZPolynomial visit( PolynomialNegation pn, Void param )
    {
        return pn.operand.accept( this,( Void ) null ).neg();
    }
    
    public XYZPolynomial visit( PolynomialDoubleDivision pdd, Void param )
    {
        XYZPolynomial dividend = pdd.dividend.accept( this,( Void ) null );
        double divisor = pdd.divisor.accept( this.valueCalculator, ( Void ) null );

        return dividend.mult( divisor );
    }
    
    public XYZPolynomial visit( PolynomialVariable pv, Void param )
    {
        switch( pv.variable )
        {
            case x:
                return XYZPolynomial.X;
            case y:
                return XYZPolynomial.Y;
            case z:
                return XYZPolynomial.Z;
            default:
                throw new UnsupportedOperationException();                
        }
    }
    
    public XYZPolynomial visit( DoubleBinaryOperation dbop, Void param )
    {
        return new XYZPolynomial( dbop.accept( this.valueCalculator, ( Void ) null ) );
    }
    
    public XYZPolynomial visit( DoubleUnaryOperation duop, Void param )
    {
        return new XYZPolynomial( duop.accept( this.valueCalculator, ( Void ) null ) );
    }

    public XYZPolynomial visit( DoubleVariable dv, Void param )
    {
        throw new UnsupportedOperationException( "no value has been assigned to parameter '" + dv.name + "'" );
    }
    
    public XYZPolynomial visit( DoubleValue dv, Void param )
    {
        return new XYZPolynomial( dv.value );
    }
}
